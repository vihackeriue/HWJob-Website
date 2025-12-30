from pydantic import BaseModel, Field
from typing import List, Optional

class RecommendJobsRequestDTO(BaseModel):
    """
    DTO cho request gợi ý việc làm cho một ứng viên.
    Chứa profile của ứng viên.
    """
    summary: str
    education: str
    # [SỬA LẠI] Cho phép skills có thể null và cung cấp giá trị mặc định là list rỗng
    skills: Optional[List[str]] = Field(default_factory=list)
