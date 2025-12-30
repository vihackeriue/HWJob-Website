from flask import Flask
from app.config import Config

def create_app(config_class=Config):
    """
    Application Factory: Tạo và cấu hình một instance của ứng dụng Flask.
    """
    app = Flask(__name__)
    app.config.from_object(config_class)

    # Dùng app_context để đảm bảo các thành phần AI được load
    # cùng với app, tránh lỗi "out of context".
    with app.app_context():
        print("--- Initializing Flask App ---")
        # Import ở đây để đảm bảo config đã được load
        from app.core import ai_core
        
        # Kiểm tra xem các thành phần AI đã load thành công chưa
        if not ai_core.embedding_model:
            raise RuntimeError("Embedding model could not be loaded. Application cannot start.")
        if not ai_core.job_collection or not ai_core.candidate_collection:
            raise RuntimeError("ChromaDB collections could not be initialized. Application cannot start.")
        
        print("AI Core components are ready.")

    # Đăng ký các Blueprints (nhóm các routes)
    from app.routes.indexing_routes import indexing_bp
    from app.routes.recommendation_routes import recommendation_bp
    from app.routes.cleanup_routes import cleanup_bp  # [MỚI] Import blueprint dọn dẹp

    # Đăng ký blueprint mà không có prefix
    app.register_blueprint(indexing_bp)
    app.register_blueprint(recommendation_bp)
    app.register_blueprint(cleanup_bp)  # [MỚI] Đăng ký blueprint dọn dẹp
    print("Blueprints registered.")

    # Tạo một route đơn giản để kiểm tra server có đang chạy không
    @app.route('/health')
    def health_check():
        return "OK", 200

    print("--- Flask App Initialized Successfully ---")
    return app
