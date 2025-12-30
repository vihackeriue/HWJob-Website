from typing import Dict

# ==================== DYNAMIC WEIGHT CONFIGURATION ====================
# Bảng ánh xạ trọng số, tách biệt cấu hình khỏi logic.
# Dễ dàng thêm các level mới (ví dụ: 'principal', 'architect') ở đây.
WEIGHT_MAP = {
    # Từ khóa (viết thường): {trọng số skill, trọng số semantic}
    'intern': {'skill': 0.3, 'semantic': 0.7},
    'thực tập': {'skill': 0.3, 'semantic': 0.7},
    'fresher': {'skill': 0.4, 'semantic': 0.6},
    'junior': {'skill': 0.5, 'semantic': 0.5},
    'senior': {'skill': 0.7, 'semantic': 0.3},
    'manager': {'skill': 0.6, 'semantic': 0.4},
    'trưởng phòng': {'skill': 0.6, 'semantic': 0.4},
    'lead': {'skill': 0.7, 'semantic': 0.3},
}
# Trọng số mặc định nếu không tìm thấy level phù hợp
DEFAULT_WEIGHTS = {'skill': 0.5, 'semantic': 0.5}


def get_dynamic_weights(job_level: str) -> Dict[str, float]:
    """
    Trả về trọng số phù hợp theo cấp độ công việc bằng cách tra cứu trong WEIGHT_MAP.
    Hàm này có khả năng mở rộng cao, chỉ cần cập nhật WEIGHT_MAP khi có level mới.
    
    Args:
        job_level: Chuỗi mô tả cấp độ công việc.
        
    Returns:
        Một dictionary chứa trọng số cho 'skill' và 'semantic'.
    """
    if not job_level or not isinstance(job_level, str):
        return DEFAULT_WEIGHTS

    level_lower = job_level.lower()

    # Duyệt qua bảng ánh xạ để tìm từ khóa phù hợp
    for keyword, weights in WEIGHT_MAP.items():
        if keyword in level_lower:
            return weights

    # Nếu không tìm thấy từ khóa nào, trả về trọng số mặc định
    return DEFAULT_WEIGHTS
