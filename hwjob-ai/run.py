import os
from app import create_app
from dotenv import load_dotenv

# Tải các biến môi trường từ file .env
load_dotenv()

# Tạo instance của ứng dụng Flask từ application factory
app = create_app()

if __name__ == '__main__':
    port = int(os.getenv("PORT", 5000))
    print(f"Starting AI Server in DEBUG mode on http://0.0.0.0:{port}")
    app.run(host='0.0.0.0', port=port, debug=True)
