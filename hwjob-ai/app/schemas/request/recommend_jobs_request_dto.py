from pydantic import BaseModel

class RecommendJobsRequestDTO(BaseModel):
    """
    DTO cho request gợi ý việc làm cho một ứng viên.
    Chứa profile của ứng viên.
    """
    summary: str
    education: str
    skills: str
