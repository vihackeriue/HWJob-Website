from pydantic import BaseModel
from typing import Optional

class SearchJobRequestDTO(BaseModel):
    """
    DTO cho việc tìm kiếm Job theo từ khóa.
    """
    keyword: str
    top_k: Optional[int] = 10
    level: Optional[str] = None  # Cho phép lọc theo level (Junior, Senior...)
