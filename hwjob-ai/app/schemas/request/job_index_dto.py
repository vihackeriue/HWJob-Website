from pydantic import BaseModel, Field
from typing import List, Optional

class JobIndexDTO(BaseModel):
    """
    DTO cho việc index một Job Post.
    """
    job_id: str
    title: str
    description: str
    skills: Optional[List[str]] = Field(default_factory=list)
    level: str
    ended_time: str
    status: str  # [MỚI] Thêm trường status ('PUBLIC', 'PRIVATE')
