from functools import wraps
from flask import request, jsonify
from app.config import Config


def require_api_key(f):
    """
    Decorator để yêu cầu xác thực bằng API Key.
    Nó sẽ kiểm tra header 'Api-key' trong mỗi request.
    """

    @wraps(f)
    def decorated_function(*args, **kwargs):
        # Lấy API key từ header của request
        provided_key = request.headers.get('Api-key')

        # Kiểm tra xem server có được cấu hình API Key không
        if not Config.API_KEY:
            # Nếu server không có key, cho phép truy cập (hữu ích cho môi trường dev)
            # Hoặc bạn có thể trả về lỗi ở đây để bắt buộc phải có key
            print("Warning: API_KEY is not set on the server. Allowing request without authentication.")
            return f(*args, **kwargs)

        # So sánh key được cung cấp với key của server
        if not provided_key or provided_key != Config.API_KEY:
            return jsonify({"error": "Unauthorized", "message": "Invalid or missing API Key."}), 401

        # Nếu key hợp lệ, cho phép thực thi hàm gốc
        return f(*args, **kwargs)

    return decorated_function
