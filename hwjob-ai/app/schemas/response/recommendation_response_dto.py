from pydantic import BaseModel
from typing import List
from .ranked_item_dto import RankedItemDTO

class RecommendationResponseDTO(BaseModel):
    """
    DTO chuẩn cho response của các API gợi ý và xếp hạng.
    """
    results: List[RankedItemDTO]
