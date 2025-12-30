from app.core.ai_core import embedding_model, job_collection, candidate_collection
from app.schemas.request.candidate_index_dto import CandidateIndexDTO
from app.schemas.request.job_index_dto import JobIndexDTO
from app.utils.text_processing import preprocess_vietnamese_text


class IndexingService:
    """
    Lớp dịch vụ xử lý các tác vụ liên quan đến việc indexing dữ liệu vào ChromaDB.
    """

    @staticmethod
    def index_job(data: JobIndexDTO):
        """
        Vector hóa và lưu trữ thông tin của một Job Post.
        Sử dụng 'upsert' để vừa có thể tạo mới, vừa có thể cập nhật.
        """
        print(f"Indexing job {data.job_id}...")
        # 1. Chuẩn bị văn bản để tạo vector
        semantic_text = f"{data.title} {data.description} {data.skills}"
        processed_text = preprocess_vietnamese_text(semantic_text)

        # 2. Tạo vector
        vector = embedding_model.encode(processed_text).tolist()

        # 3. Lưu vào ChromaDB
        job_collection.upsert(
            ids=[str(data.job_id)],
            embeddings=[vector],
            metadatas=[{"skills": data.skills, "level": data.level}],
            documents=[processed_text]  # Lưu text đã xử lý để debug
        )
        print(f"Job {data.job_id} indexed successfully.")

    @staticmethod
    def index_candidate(data: CandidateIndexDTO):
        """
        Vector hóa và lưu trữ thông tin của một Candidate.
        Sử dụng 'upsert' để vừa có thể tạo mới, vừa có thể cập nhật.
        """
        print(f"Indexing candidate {data.candidate_id}...")
        # 1. Chuẩn bị văn bản để tạo vector
        semantic_text = f"{data.summary} {data.education} {data.skills}"
        processed_text = preprocess_vietnamese_text(semantic_text)

        # 2. Tạo vector
        vector = embedding_model.encode(processed_text).tolist()

        # 3. Lưu vào ChromaDB
        candidate_collection.upsert(
            ids=[str(data.candidate_id)],
            embeddings=[vector],
            metadatas=[{"skills": data.skills}],
            documents=[processed_text]  # Lưu text đã xử lý để debug
        )
        print(f"Candidate {data.candidate_id} indexed successfully.")
