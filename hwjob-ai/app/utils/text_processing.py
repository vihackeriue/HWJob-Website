import re
from typing import Any
from underthesea import word_tokenize
from app.config import Config


def _load_vietnamese_stopwords(path: str) -> set:
    """
    Hàm nội bộ để tải danh sách stopwords từ file.
    """
    try:
        with open(path, "r", encoding="utf-8") as f:
            return set(line.strip() for line in f if line.strip())
    except FileNotFoundError:
        print(f"Warning: Stopwords file not found at {path}. Proceeding without stopwords.")
        return set()


# Load stopwords một lần duy nhất khi module được import
vietnamese_stopwords = _load_vietnamese_stopwords(Config.STOPWORDS_PATH)
print(f"Loaded {len(vietnamese_stopwords)} Vietnamese stopwords.")


def preprocess_vietnamese_text(text: Any) -> str:
    """
    Chuỗi các bước tiền xử lý cho văn bản tiếng Việt:
    1. Loại bỏ thẻ HTML.
    2. Chuyển thành chữ thường.
    3. Loại bỏ email và URL.
    4. Loại bỏ các ký tự không cần thiết.
    5. Tách từ (tokenize).
    6. Loại bỏ stopwords và các từ ngắn.
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

    # 5. Tách từ bằng underthesea
    tokens = word_tokenize(text, format="text").split()

    # 6. Loại bỏ stopwords và các từ không hợp lệ
    clean_tokens = [t for t in tokens if t not in vietnamese_stopwords and not t.isdigit() and len(t) > 1]

    return " ".join(clean_tokens)
