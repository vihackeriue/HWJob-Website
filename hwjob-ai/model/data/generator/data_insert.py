import json
import os
import pandas as pd
from sqlalchemy import create_engine, text
import bcrypt
import random
import uuid

# Database connection configuration
# Hardcoded connection string with username: root, password: root
DB_CONNECTION_STRING = "mysql+pymysql://root:root@localhost:3306/hwjob_db"

DATA_FILE = "data_insert.json"

def load_data(file_path):
    """Load data from JSON file."""
    if not os.path.exists(file_path):
        print(f"File {file_path} not found.")
        return None
    
    with open(file_path, 'r', encoding='utf-8') as f:
        return json.load(f)

def hash_password(password):
    """Hash password using bcrypt."""
    if not password:
        return None
    hashed = bcrypt.hashpw(password.encode('utf-8'), bcrypt.gensalt(10))
    return hashed.decode('utf-8')

def get_id_by_field(engine, table, field, value):
    """Get ID from table where field = value."""
    try:
        with engine.connect() as conn:
            # Use parameterized query to prevent injection
            query = text(f"SELECT id FROM {table} WHERE {field} = :val LIMIT 1")
            result = conn.execute(query, {"val": value}).fetchone()
            return result[0] if result else None
    except Exception as e:
        print(f"Error lookup {table} by {field}={value}: {e}")
        return None

def get_existing_ids(engine, table_name):
    """Fetch all IDs from a table."""
    try:
        with engine.connect() as conn:
            result = conn.execute(text(f"SELECT id FROM {table_name}"))
            return [row[0] for row in result]
    except Exception as e:
        print(f"Error fetching IDs from {table_name}: {e}")
        return []

def check_exists(engine, table, field, value):
    """Check if a record exists."""
    return get_id_by_field(engine, table, field, value) is not None

def init_roles(engine):
    """Initialize default roles if they don't exist."""
    roles = [
        {"name": "CANDIDATE", "description": "Candidate Role"},
        {"name": "RECRUITER", "description": "Recruiter Role"},
        {"name": "ADMIN", "description": "Admin Role"}
    ]
    
    print("Initializing roles...")
    for role in roles:
        # Check by NAME, not ID
        if not check_exists(engine, "roles", "name", role["name"]):
            try:
                # Generate UUID for role ID
                role["id"] = str(uuid.uuid4())
                df = pd.DataFrame([role])
                df.to_sql("roles", con=engine, if_exists='append', index=False)
                print(f"Created role: {role['name']}")
            except Exception as e:
                print(f"Error creating role {role['name']}: {e}")
        else:
            # print(f"Role {role['name']} already exists.")
            pass

def assign_role(engine, user_id, role_name):
    """Assign a role to a user by looking up role ID from name."""
    try:
        # Lookup Role ID from Role Name
        role_id = get_id_by_field(engine, "roles", "name", role_name)
        if not role_id:
            print(f"Error: Role '{role_name}' not found in database.")
            return

        # Check if assignment already exists
        with engine.connect() as conn:
            res = conn.execute(text("SELECT 1 FROM users_roles WHERE user_id=:u AND roles_id=:r"), {"u": user_id, "r": role_id}).fetchone()
            if not res:
                conn.execute(text("INSERT INTO users_roles (user_id, roles_id) VALUES (:u, :r)"), {"u": user_id, "r": role_id})
                conn.commit()
                print(f"Assigned role {role_name} to user {user_id}")
    except Exception as e:
        print(f"Error assigning role {role_name} to user {user_id}: {e}")

def insert_data(engine, data):
    """Insert data into database tables."""
    
    # Initialize roles first
    init_roles(engine)
    
    table_mapping = {
        "skills": "skills",
        "industries": "industries",
        "levels": "levels",
        "job_types": "job_types",
        "users": "users",
        "candidates": "candidates",
        "recruiters": "recruiters",
        "job_posts": "job_posts",
        "applications": "applications"
    }
    
    insertion_order = [
        "skills", 
        "industries", 
        "levels", 
        "job_types", 
        "users", 
        "candidates", 
        "recruiters", 
        "job_posts", 
        "applications"
    ]

    # Local cache to store generated IDs for linking within the same run
    generated_ids = {
        "users": {},
        "job_posts": {}
    }

    for key in insertion_order:
        if key not in data:
            continue
            
        rows = data[key]
        if not rows:
            continue
            
        table_name = table_mapping.get(key, key)
        print(f"Processing {key} -> {table_name} ({len(rows)} rows)...")
        
        clean_rows = []
        candidate_skills_to_insert = []
        job_post_skills_to_insert = []
        users_to_assign_roles = [] # List of (user_id, role_name)
        
        db_cache_ids = {}

        for row in rows:
            # 1. Handle Skills (Unique Name)
            if key == "skills":
                if check_exists(engine, "skills", "name", row["name"]):
                    print(f"Skipping skill '{row['name']}': Already exists.")
                    continue
                clean_rows.append(row)

            # 2. Handle Industries (Unique Name)
            elif key == "industries":
                if check_exists(engine, "industries", "name", row["name"]):
                    print(f"Skipping industry '{row['name']}': Already exists.")
                    continue
                clean_rows.append(row)

            # 3. Handle Levels (Unique Name)
            elif key == "levels":
                if check_exists(engine, "levels", "name", row["name"]):
                    print(f"Skipping level '{row['name']}': Already exists.")
                    continue
                clean_rows.append(row)

            # 4. Handle Job Types (Unique Code)
            elif key == "job_types":
                if check_exists(engine, "job_types", "code", row["code"]):
                    print(f"Skipping job_type '{row['code']}': Already exists.")
                    continue
                clean_rows.append(row)

            # 5. Handle Users (Unique Username)
            elif key == "users":
                if check_exists(engine, "users", "username", row["username"]):
                    print(f"Skipping user '{row['username']}': Already exists.")
                    existing_id = get_id_by_field(engine, "users", "username", row["username"])
                    generated_ids["users"][row["username"]] = existing_id
                    continue
                
                if "password" in row:
                    row["password"] = hash_password(row["password"])
                
                # Set default image_url for all users
                row["image_url"] = "http://localhost:8080/hwjob/api/public/media/default-avatar.png"
                
                if "id" not in row:
                    new_id = str(uuid.uuid4())
                    row["id"] = new_id
                    if "username" in row:
                        generated_ids["users"][row["username"]] = new_id
                
                clean_rows.append(row)

            # 6. Handle Candidates
            elif key == "candidates":
                user_id = None
                username = row.get("username")
                
                if username and username in generated_ids["users"]:
                    user_id = generated_ids["users"][username]
                elif username:
                    user_id = get_id_by_field(engine, "users", "username", username)
                
                if user_id and check_exists(engine, "candidates", "id", user_id):
                    print(f"Skipping candidate for user '{username}': Already exists.")
                    users_to_assign_roles.append((user_id, "CANDIDATE"))
                    continue

                if not user_id:
                     pass
                
                if user_id:
                    row["id"] = user_id
                    users_to_assign_roles.append((user_id, "CANDIDATE"))
                    
                    if "skill_names" in row:
                        skill_names = row.pop("skill_names")
                        for s_name in skill_names:
                            s_id = get_id_by_field(engine, "skills", "name", s_name)
                            if s_id:
                                candidate_skills_to_insert.append({"candidate_id": user_id, "skills_id": s_id})
                    
                    if "username" in row: del row["username"]
                    clean_rows.append(row)

            # 7. Handle Recruiters
            elif key == "recruiters":
                user_id = None
                username = row.get("username")

                if username and username in generated_ids["users"]:
                    user_id = generated_ids["users"][username]
                elif username:
                    user_id = get_id_by_field(engine, "users", "username", username)
                
                if user_id and check_exists(engine, "recruiters", "id", user_id):
                    print(f"Skipping recruiter for user '{username}': Already exists.")
                    users_to_assign_roles.append((user_id, "RECRUITER"))
                    continue

                if user_id:
                    row["id"] = user_id
                    users_to_assign_roles.append((user_id, "RECRUITER"))
                    if "username" in row: del row["username"]
                    clean_rows.append(row)

            # 8. Handle Job Posts
            elif key == "job_posts":
                if "id" not in row:
                    new_id = str(uuid.uuid4())
                    row["id"] = new_id

                # Lookup FKs
                if "recruiter_username" in row:
                    rec_username = row["recruiter_username"]
                    rec_user_id = None
                    if rec_username in generated_ids["users"]:
                        rec_user_id = generated_ids["users"][rec_username]
                    else:
                        rec_user_id = get_id_by_field(engine, "users", "username", rec_username)
                    
                    if rec_user_id: row["recruiter_id"] = rec_user_id
                    del row["recruiter_username"]
                
                if "industry_name" in row:
                    ind_id = get_id_by_field(engine, "industries", "name", row["industry_name"])
                    if ind_id: row["industry_id"] = ind_id
                    del row["industry_name"]
                
                if "job_type_code" in row:
                    jt_id = get_id_by_field(engine, "job_types", "code", row["job_type_code"])
                    if jt_id: row["job_type_id"] = jt_id
                    del row["job_type_code"]

                if "level_name" in row:
                    lvl_id = get_id_by_field(engine, "levels", "name", row["level_name"])
                    if lvl_id: row["level_id"] = lvl_id
                    del row["level_name"]

                # Fallback FKs
                if "recruiter_id" not in row:
                     if "recruiters" not in db_cache_ids: db_cache_ids["recruiters"] = get_existing_ids(engine, "recruiters")
                     if db_cache_ids["recruiters"]: row["recruiter_id"] = random.choice(db_cache_ids["recruiters"])
                
                if "industry_id" not in row:
                    if "industries" not in db_cache_ids: db_cache_ids["industries"] = get_existing_ids(engine, "industries")
                    if db_cache_ids["industries"]: row["industry_id"] = random.choice(db_cache_ids["industries"])

                if "job_type_id" not in row:
                    if "job_types" not in db_cache_ids: db_cache_ids["job_types"] = get_existing_ids(engine, "job_types")
                    if db_cache_ids["job_types"]: row["job_type_id"] = random.choice(db_cache_ids["job_types"])
                
                if "level_id" not in row:
                    if "levels" not in db_cache_ids: db_cache_ids["levels"] = get_existing_ids(engine, "levels")
                    if db_cache_ids["levels"]: row["level_id"] = random.choice(db_cache_ids["levels"])

                skill_names = row.pop("skill_names", [])
                if skill_names and "id" in row:
                    job_id = row["id"]
                    if "title" in row:
                        generated_ids["job_posts"][row["title"]] = job_id
                    for s_name in skill_names:
                        s_id = get_id_by_field(engine, "skills", "name", s_name)
                        if s_id:
                            job_post_skills_to_insert.append({"job_post_id": job_id, "skills_id": s_id})

                clean_rows.append(row)

            # 9. Handle Applications
            elif key == "applications":
                cand_id = None
                if "candidate_username" in row:
                    c_username = row["candidate_username"]
                    if c_username in generated_ids["users"]:
                        cand_id = generated_ids["users"][c_username]
                    else:
                        cand_id = get_id_by_field(engine, "users", "username", c_username)
                    del row["candidate_username"]
                
                job_id = None
                if "job_post_title" in row:
                    jp_title = row["job_post_title"]
                    if jp_title in generated_ids["job_posts"]:
                        job_id = generated_ids["job_posts"][jp_title]
                    else:
                        job_id = get_id_by_field(engine, "job_posts", "title", jp_title)
                    del row["job_post_title"]
                
                # Fallback
                if not cand_id:
                    if "candidates" not in db_cache_ids: db_cache_ids["candidates"] = get_existing_ids(engine, "candidates")
                    if db_cache_ids["candidates"]: cand_id = random.choice(db_cache_ids["candidates"])
                
                if not job_id:
                    if "job_posts" not in db_cache_ids: db_cache_ids["job_posts"] = get_existing_ids(engine, "job_posts")
                    if db_cache_ids["job_posts"]: job_id = random.choice(db_cache_ids["job_posts"])
                
                if cand_id and job_id:
                    try:
                        with engine.connect() as conn:
                            res = conn.execute(text("SELECT 1 FROM applications WHERE candidate_id=:c AND job_post_id=:j"), {"c": cand_id, "j": job_id}).fetchone()
                            if res:
                                print(f"Skipping application: Already exists.")
                                continue
                    except:
                        pass

                    row["candidate_id"] = cand_id
                    row["job_post_id"] = job_id
                    clean_rows.append(row)

        # Execute Insert
        if clean_rows:
            df = pd.DataFrame(clean_rows)
            if key in ["skills", "industries", "levels", "job_types"] and 'id' in df.columns:
                df = df.drop(columns=['id'])
            
            try:
                df.to_sql(table_name, con=engine, if_exists='append', index=False)
                print(f"Successfully inserted {len(clean_rows)} rows into {table_name}.")
            except Exception as e:
                print(f"Error inserting into {table_name}: {e}")

        # Assign Roles
        if users_to_assign_roles:
            print(f"Assigning roles for {len(users_to_assign_roles)} users...")
            for uid, role_name in users_to_assign_roles:
                assign_role(engine, uid, role_name)

        # Post-processing Skills
        if key == "candidates" and candidate_skills_to_insert:
            final_cs = []
            for item in candidate_skills_to_insert:
                try:
                    with engine.connect() as conn:
                        res = conn.execute(text("SELECT 1 FROM candidates_skills WHERE candidate_id=:c AND skills_id=:s"), {"c": item["candidate_id"], "s": item["skills_id"]}).fetchone()
                        if not res:
                            final_cs.append(item)
                except:
                    pass
            
            if final_cs:
                print(f"Inserting {len(final_cs)} candidate_skills...")
                df_cs = pd.DataFrame(final_cs)
                try:
                    df_cs.to_sql("candidates_skills", con=engine, if_exists='append', index=False)
                except Exception as e:
                    print(f"Error inserting candidate_skills: {e}")

        if key == "job_posts" and job_post_skills_to_insert:
            print(f"Inserting {len(job_post_skills_to_insert)} job_post_skills...")
            df_jps = pd.DataFrame(job_post_skills_to_insert)
            try:
                df_jps.to_sql("job_posts_skills", con=engine, if_exists='append', index=False)
            except Exception as e:
                print(f"Error inserting job_post_skills: {e}")

if __name__ == "__main__":
    current_dir = os.path.dirname(os.path.abspath(__file__))
    data_file_path = os.path.join(current_dir, DATA_FILE)
    
    print(f"Loading data from {data_file_path}...")
    data = load_data(data_file_path)
    
    if data:
        print("Connecting to database...")
        try:
            engine = create_engine(DB_CONNECTION_STRING)
            with engine.connect() as connection:
                print("Database connection successful.")
            
            insert_data(engine, data)
            print("Data insertion process finished.")
        except Exception as e:
            print(f"Database connection failed: {e}")
