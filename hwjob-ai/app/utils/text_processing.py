import re
from typing import Any
from underthesea import word_tokenize

def preprocess_vietnamese_text(text: Any) -> str:
    """
    Chuỗi các bước tiền xử lý cho văn bản tiếng Việt (đã được đơn giản hóa).
    1. Loại bỏ thẻ HTML.
    2. Chuyển thành chữ thường.
    3. Loại bỏ email và URL.
    4. Loại bỏ các ký tự không cần thiết.
    5. Tách từ (tokenize).
    """
    if not text or not isinstance(text, str):
        return ""
    
    # 1. Loại bỏ thẻ HTML
    text = re.sub(r'<[^>]+>', ' ', text)
    
    # 2. Chuyển thành chữ thường
    text = text.lower()
    
    # 3. Loại bỏ email và URL
    text = re.sub(r'\S+@\S+', ' ', text)
    text = re.sub(r'http\S+', ' ', text)
    
    # 4. Loại bỏ các ký tự đặc biệt, chỉ giữ lại chữ, số và khoảng trắng
    text = re.sub(r'[^a-zàáảãạăằắẳẵặâầấẩẫậèéẻẽẹêềếểễệìíỉĩịòóỏõọôồốổỗộơờớởỡợùúủũụưừứửữựỳýỷỹỵ0-9\s]', ' ', text)
    
    # 5. Tách từ bằng underthesea và nối lại
    # Không còn bước loại bỏ stopword
    tokens = word_tokenize(text, format="text")
    
    return tokens
