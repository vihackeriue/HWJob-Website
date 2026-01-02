from pydantic import BaseModel


class RecommendJobsRequestDTO(BaseModel):
    """
    DTO cho request gợi ý việc làm cho một ứng viên.
    [SỬA LẠI] Chỉ chứa ID của ứng viên.
    """
    candidate_id: str
