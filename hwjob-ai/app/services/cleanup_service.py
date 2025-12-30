from app.core.ai_core import job_collection, candidate_collection

class CleanupService:
    """
    Lớp dịch vụ xử lý các tác vụ liên quan đến việc dọn dẹp dữ liệu trong ChromaDB.
    """
    @staticmethod
    def delete_job(job_id: str):
        """
        Xóa một Job Post khỏi collection.
        ChromaDB's delete hoạt động an toàn ngay cả khi ID không tồn tại.
        """
        print(f"Service: Deleting job {job_id} from vector store...")
        job_collection.delete(ids=[job_id])
        print(f"Service: Deletion processed for job {job_id}.")

    @staticmethod
    def delete_candidate(candidate_id: str):
        """
        Xóa một Candidate khỏi collection.
        ChromaDB's delete hoạt động an toàn ngay cả khi ID không tồn tại.
        """
        print(f"Service: Deleting candidate {candidate_id} from vector store...")
        candidate_collection.delete(ids=[candidate_id])
        print(f"Service: Deletion processed for candidate {candidate_id}.")
