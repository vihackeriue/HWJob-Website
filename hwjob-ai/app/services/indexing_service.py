from app.core.ai_core import embedding_model, job_collection, candidate_collection
from app.utils.text_processing import preprocess_vietnamese_text
from app.schemas.request.job_index_dto import JobIndexDTO
from app.schemas.request.candidate_index_dto import CandidateIndexDTO
from datetime import datetime


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
        print(f"Indexing job {data.job_id} with status {data.status}...")

        try:
            try:
                ended_time_dt = datetime.fromisoformat(data.ended_time)
            except ValueError:
                ended_time_dt = datetime.strptime(data.ended_time, '%Y-%m-%dT%H:%M:%S')
            ended_time_timestamp = int(ended_time_dt.timestamp())
        except (ValueError, TypeError):
            print(f"Warning: Could not parse ended_time '{data.ended_time}'. Setting to 0.")
            ended_time_timestamp = 0

        skills_str = ", ".join(data.skills)

        semantic_text = f"{data.title} {data.description} {skills_str}"
        processed_text = preprocess_vietnamese_text(semantic_text)

        vector = embedding_model.encode(processed_text).tolist()

        # [SỬA LẠI] Thêm status vào metadata
        job_collection.upsert(
            ids=[str(data.job_id)],
            embeddings=[vector],
            metadatas=[{
                "skills": skills_str,
                "level": data.level,
                "ended_time": ended_time_timestamp,
                "status": data.status.upper()  # Lưu status dưới dạng chữ hoa để đồng nhất
            }],
            documents=[processed_text]
        )
        print(f"Job {data.job_id} indexed successfully.")

    @staticmethod
    def index_candidate(data: CandidateIndexDTO):
        """
        Vector hóa và lưu trữ thông tin của một Candidate.
        """
        print(f"Indexing candidate {data.candidate_id}...")
        skills_str = ", ".join(data.skills)

        semantic_text = f"{data.summary} {data.education} {skills_str}"
        processed_text = preprocess_vietnamese_text(semantic_text)

        vector = embedding_model.encode(processed_text).tolist()

        candidate_collection.upsert(
            ids=[str(data.candidate_id)],
            embeddings=[vector],
            metadatas=[{"skills": skills_str}],
            documents=[processed_text]
        )
        print(f"Candidate {data.candidate_id} indexed successfully.")
