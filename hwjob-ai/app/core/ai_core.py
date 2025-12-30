import chromadb
from sentence_transformers import SentenceTransformer
import torch
from app.config import Config

print("Initializing AI Core...")

# ==================== DEVICE CONFIGURATION ====================
# Xác định thiết bị sẽ sử dụng (GPU nếu có, nếu không thì CPU)
device = "cuda" if torch.cuda.is_available() else "cpu"
print(f"Using device: {device}")

# ==================== EMBEDDING MODEL LOADING ====================
# Load model embedding từ Hugging Face Hub và chuyển nó tới thiết bị đã chọn
try:
    embedding_model = SentenceTransformer(Config.EMBEDDING_MODEL_NAME, device=device)
    print(f"Embedding model '{Config.EMBEDDING_MODEL_NAME}' loaded successfully.")
except Exception as e:
    print(f"Error loading embedding model: {e}")
    embedding_model = None

# ==================== CHROMA DB CLIENT INITIALIZATION ====================
# Khởi tạo một PersistentClient để dữ liệu được lưu trữ trên đĩa
try:
    chroma_client = chromadb.PersistentClient(path=Config.CHROMA_DB_PATH)

    # Lấy hoặc tạo collection cho Job Posts
    job_collection = chroma_client.get_or_create_collection(
        name=Config.JOB_COLLECTION_NAME,
        metadata={"hnsw:space": "cosine"}  # Sử dụng cosine similarity để đo lường khoảng cách
    )

    # Lấy hoặc tạo collection cho Candidates
    candidate_collection = chroma_client.get_or_create_collection(
        name=Config.CANDIDATE_COLLECTION_NAME,
        metadata={"hnsw:space": "cosine"}
    )
    print(f"ChromaDB collections '{Config.JOB_COLLECTION_NAME}' and '{Config.CANDIDATE_COLLECTION_NAME}' are ready.")
except Exception as e:
    print(f"Error initializing ChromaDB: {e}")
    job_collection = None
    candidate_collection = None
