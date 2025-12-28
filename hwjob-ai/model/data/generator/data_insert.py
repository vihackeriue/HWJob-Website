import mysql.connector
import json
import random
import uuid
from datetime import datetime, timedelta

DB_CONFIG = {
    "host": "localhost",
    "user": "root",
    "password": "root",
    "database": "hwjob_db"
}


class DataInserter:

    def __init__(self):
        self.conn = None
        self.cur = None
        self.new_job_posts = []  # lưu id job_posts vừa insert

    # ================= DB =================

    def connect(self):
        self.conn = mysql.connector.connect(**DB_CONFIG)
        self.cur = self.conn.cursor(dictionary=True)

    def close(self):
        if self.cur:
            self.cur.close()
        if self.conn:
            self.conn.close()

    def load_json(self, path):
        try:
            with open(path, "r", encoding="utf-8") as f:
                return json.load(f)
        except:
            return {}

    def load_ids(self, table):
        self.cur.execute(f"SELECT id FROM {table}")
        return [r["id"] for r in self.cur.fetchall()]

    def rand_time(self, days):
        return datetime.now() - timedelta(days=random.randint(0, days))

    # ================= INSERT JOB POSTS =================

    def insert_job_posts(self, jobs):
        recruiters = self.load_ids("recruiters")
        industries = self.load_ids("industries")
        job_types = self.load_ids("job_types")
        levels = self.load_ids("levels")
        regions = self.load_ids("regions")

        print("\n--- INSERT JOB POSTS ---")

        for j in jobs:
            job_id = str(uuid.uuid4())

            self.cur.execute("""
                             INSERT INTO job_posts (id, title, description, salary, salary_type,
                                                    quantity, status,
                                                    created_at, updated_at, ended_time,
                                                    recruiter_id, industry_id, job_type_id, level_id, region_id)
                             VALUES (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)
                             """, (
                                 job_id,
                                 j.get("title"),
                                 j.get("description"),
                                 j.get("salary"),
                                 j.get("salary_type"),
                                 j.get("quantity"),
                                 j.get("status"),
                                 self.rand_time(180),
                                 self.rand_time(30),
                                 j.get("ended_time"),
                                 random.choice(recruiters),
                                 random.choice(industries),
                                 random.choice(job_types),
                                 random.choice(levels),
                                 random.choice(regions)
                             ))

            self.new_job_posts.append(job_id)
            print(f"✓ JobPost inserted: {job_id}")

    # ================= JOB_POSTS_SKILLS =================

    def insert_job_post_skills(self, min_skill=2, max_skill=6):
        print("\n--- INSERT JOB_POSTS_SKILLS ---")

        if not self.new_job_posts:
            print("⚠ No new job posts")
            return

        skills = self.load_ids("skills")

        for job_id in self.new_job_posts:
            selected = random.sample(
                skills,
                random.randint(min_skill, min(max_skill, len(skills)))
            )

            for skill_id in selected:
                self.cur.execute("""
                                 INSERT
                                 IGNORE INTO job_posts_skills (job_post_id, skills_id)
                    VALUES (
                                 %s,
                                 %s
                                 )
                                 """, (job_id, skill_id))

            print(f"✓ JobPost {job_id} → {len(selected)} skills")

    # ================= APPLICATIONS =================

    def insert_applications(self, apps):
        print("\n--- INSERT APPLICATIONS ---")
        jobs = self.load_ids("job_posts")
        candidates = self.load_ids("candidates")

        for a in apps:
            self.cur.execute("""
                             INSERT
                             IGNORE INTO applications (
                    candidate_id, job_post_id,
                    status, created_at, updated_at
                )
                VALUES (
                             %s,
                             %s,
                             %s,
                             %s,
                             %s
                             )
                             """, (
                                 random.choice(candidates),
                                 random.choice(jobs),
                                 a.get("status"),
                                 self.rand_time(90),
                                 self.rand_time(30)
                             ))

    # ================= CANDIDATE_SKILLS =================

    def auto_candidate_skills(self, min_skill=1, max_skill=5):
        print("\n--- AUTO CANDIDATE_SKILLS ---")

        candidates = self.load_ids("candidates")
        skills = self.load_ids("skills")

        for cid in candidates:
            chosen = random.sample(
                skills,
                random.randint(min_skill, min(max_skill, len(skills)))
            )
            for sid in chosen:
                self.cur.execute("""
                                 INSERT
                                 IGNORE INTO candidates_skills (candidate_id, skills_id)
                    VALUES (
                                 %s,
                                 %s
                                 )
                                 """, (cid, sid))

    # ================= RUN =================

    def run(self, insert_file="data_insert.json"):
        print("=== START DATA INSERT ===")
        self.connect()
        data = self.load_json(insert_file)

        try:
            self.insert_job_posts(data.get("job_posts", []))
            self.insert_job_post_skills()
            self.insert_applications(data.get("applications", []))
            self.auto_candidate_skills()

            self.conn.commit()
            print("\n✅ INSERT SUCCESS")
        except Exception as e:
            self.conn.rollback()
            print("\n❌ ERROR:", e)
        finally:
            self.close()


if __name__ == "__main__":
    DataInserter().run()
