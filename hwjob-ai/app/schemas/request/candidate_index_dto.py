from pydantic import BaseModel

class CandidateIndexDTO(BaseModel):
    """
    DTO cho việc index một Candidate.
    Dữ liệu được gửi từ Spring Boot khi có Candidate mới hoặc cập nhật.
    """
    candidate_id: int
    summary: str
    education: str
    skills: str
