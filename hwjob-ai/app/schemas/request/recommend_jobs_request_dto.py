from pydantic import BaseModel, Field
from typing import List, Optional

class RecommendJobsRequestDTO(BaseModel):
    """
    DTO cho request gợi ý việc làm cho một ứng viên.
    Chứa ID của ứng viên, danh sách ID các job đã lưu, và số lượng kết quả mong muốn.
    """
    candidate_id: str
    saved_job_ids: Optional[List[str]] = Field(default_factory=list)
    n_results: Optional[int] = 100  # [MỚI] Thêm n_results vào body, mặc định là 100
