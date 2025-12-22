import mysql.connector
import json
import random
from datetime import datetime

DB_CONFIG = {
    "host": "localhost",
    "user": "root",
    "password": "root",
    "database": "hwjob_db"
}


class DataUpdater:

    def __init__(self):
        self.conn = None
        self.cur = None

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

    # ================= UPDATE USERS =================

    def update_users(self, users):
        print("\n--- UPDATE USERS ---")
        user_ids = self.load_ids("users")

        for u in users:
            self.cur.execute("""
                             UPDATE users
                             SET full_name  = %s,
                                 phone      = %s,
                                 summary    = %s,
                                 updated_at = %s
                             WHERE id = %s
                             """, (
                                 u.get("full_name"),
                                 u.get("phone"),
                                 u.get("summary"),
                                 datetime.now(),
                                 random.choice(user_ids)
                             ))

    # ================= UPDATE CANDIDATES =================

    def update_candidates(self, candidates):
        print("\n--- UPDATE CANDIDATES ---")
        candidate_ids = self.load_ids("candidates")

        for c in candidates:
            dob = c.get("dob")
            if isinstance(dob, str):
                dob = datetime.strptime(dob, "%Y-%m-%d").date()

            self.cur.execute("""
                             UPDATE candidates
                             SET dob=%s,
                                 education=%s,
                                 expect_salary=%s,
                                 gender=%s
                             WHERE id = %s
                             """, (
                                 dob,
                                 c.get("education"),
                                 c.get("expect_salary"),
                                 c.get("gender"),
                                 random.choice(candidate_ids)
                             ))

    # ================= UPDATE RECRUITERS =================

    def update_recruiters(self, recruiters):
        print("\n--- UPDATE RECRUITERS ---")
        recruiter_ids = self.load_ids("recruiters")

        for r in recruiters:
            self.cur.execute("""
                             UPDATE recruiters
                             SET website=%s
                             WHERE id = %s
                             """, (
                                 r.get("website"),
                                 random.choice(recruiter_ids)
                             ))

    # ================= RUN =================

    def run(self, update_file="data_update.json"):
        print("=== START DATA UPDATE ===")
        self.connect()
        data = self.load_json(update_file)

        try:
            self.update_users(data.get("users", []))
            self.update_candidates(data.get("candidates", []))
            self.update_recruiters(data.get("recruiters", []))

            self.conn.commit()
            print("\n✅ UPDATE SUCCESS")
        except Exception as e:
            self.conn.rollback()
            print("\n❌ ERROR:", e)
        finally:
            self.close()


if __name__ == "__main__":
    DataUpdater().run()




