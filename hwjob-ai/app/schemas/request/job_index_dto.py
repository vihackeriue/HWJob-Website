from pydantic import BaseModel

class JobIndexDTO(BaseModel):
    """
    DTO cho việc index một Job Post.
    Dữ liệu được gửi từ Spring Boot khi có Job mới hoặc cập nhật.
    """
    job_id: int
    title: str
    description: str
    skills: str
    level: str
