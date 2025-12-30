from pydantic import BaseModel, Field
from typing import List

class RankCandidatesRequestDTO(BaseModel):
    """
    DTO cho request xếp hạng các ứng viên đã apply.
    Chứa thông tin job và danh sách ID của các ứng viên đang chờ.
    """
    title: str
    description: str
    skills: str
    level: str
    pending_candidate_ids: List[int] = Field(default_factory=list)
