from typing import Dict

# ==================== DEFAULT WEIGHT CONFIGURATION ====================
# Hệ thống sử dụng trọng số cân bằng cho tất cả các trường hợp.
DEFAULT_WEIGHTS = {'skill': 0.3, 'semantic': 0.7}


def get_dynamic_weights() -> Dict[str, float]:
    """
    Trả về trọng số để tính điểm phù hợp.
    Hiện tại trả về trọng số mặc định (50/50).
    
    Returns:
        Một dictionary chứa trọng số cho 'skill' và 'semantic'.
    """
    return DEFAULT_WEIGHTS
