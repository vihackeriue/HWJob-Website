import os


class Config:
    """
    Lớp cấu hình tập trung cho ứng dụng AI Server.
    """
    # ==================== PATHS ====================
    # Lấy đường dẫn gốc của thư mục app
    APP_DIR = os.path.dirname(__file__)
    # Đi lên một cấp để ra thư mục gốc của project (hwjob-ai)
    PROJECT_ROOT = os.path.abspath(os.path.join(APP_DIR, '..'))

    # Đường dẫn tới file stopwords, nằm trong thư mục model/data
    STOPWORDS_PATH = os.path.join(PROJECT_ROOT, 'model', 'data', 'nlp', 'vietnamese-stopwords.txt')

    CHROMA_DB_PATH = os.path.join(PROJECT_ROOT, 'chroma_db_data')

    # ==================== AI MODELS ====================
    # Tên model embedding sẽ được sử dụng
    EMBEDDING_MODEL_NAME = 'sentence-transformers/paraphrase-multilingual-MiniLM-L12-v2'

    # ==================== CHROMA DB ====================
    # Tên các collection trong ChromaDB
    JOB_COLLECTION_NAME = "job_posts"
    CANDIDATE_COLLECTION_NAME = "candidates"
