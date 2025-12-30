from pydantic import BaseModel, Field
from typing import List, Optional

class CandidateIndexDTO(BaseModel):
    """
    DTO cho việc index một Candidate.
    Dữ liệu được gửi từ Spring Boot khi có Candidate mới hoặc cập nhật.
    """
    candidate_id: str
    summary: str
    education: str
    # [SỬA LẠI] Cho phép skills có thể null và cung cấp giá trị mặc định là list rỗng
    skills: Optional[List[str]] = Field(default_factory=list)
