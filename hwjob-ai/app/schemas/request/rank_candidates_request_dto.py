from pydantic import BaseModel, Field
from typing import List

class RankCandidatesRequestDTO(BaseModel):
    """
    DTO cho request xếp hạng các ứng viên đã apply.
    [SỬA LẠI] Chỉ chứa ID của job và danh sách ID của các ứng viên đang chờ.
    """
    job_id: str
    pending_candidate_ids: List[str] = Field(default_factory=list)
