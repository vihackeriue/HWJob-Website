import os
from app import create_app
from dotenv import load_dotenv

# Tải các biến môi trường từ file .env
load_dotenv()

# Tạo instance của ứng dụng Flask từ application factory
app = create_app()

if __name__ == '__main__':
    app.run(debug=True)
