from pydantic import BaseModel

class RankedItemDTO(BaseModel):
    """
    DTO cho một item (Job hoặc Candidate) đã được xếp hạng.
    """
    id: str
    score: float
