from flask import Blueprint, request, jsonify
from pydantic import ValidationError
from app.schemas.request.recommend_jobs_request_dto import RecommendJobsRequestDTO
from app.schemas.request.rank_candidates_request_dto import RankCandidatesRequestDTO
from app.schemas.response.recommendation_response_dto import RecommendationResponseDTO
from app.services.recommendation_service import RecommendationService
from app.core.security import require_api_key

# Tạo một Blueprint cho các route liên quan đến gợi ý
recommendation_bp = Blueprint('recommendation_bp', __name__)

@recommendation_bp.route('/hello', methods=['GET'])
@require_api_key
def hello_world():
    """
    Một route đơn giản để kiểm tra xác thực API Key.
    """
    return jsonify({"message": "Hello, you are authenticated!"}), 200

@recommendation_bp.route('/recommend-jobs', methods=['POST'])
@require_api_key
def recommend_jobs():
    """
    API endpoint để gợi ý việc làm cho ứng viên.
    Có thể nhận một query param `n_results` để giới hạn số lượng kết quả.
    """
    try:
        # [MỚI] Lấy n_results từ query param, mặc định là 100 nếu không có
        n_results = request.args.get('n_results', default=100, type=int)

        # Validate dữ liệu đầu vào từ body
        data = RecommendJobsRequestDTO(**request.json)
        
        # Gọi service để lấy kết quả, truyền n_results vào
        results = RecommendationService.recommend_jobs(data, top_k=n_results)
        
        # Đóng gói response theo DTO
        response_dto = RecommendationResponseDTO(results=results)
        return jsonify(response_dto.dict()), 200
    except ValidationError as e:
        return jsonify({"error": "Invalid input data", "details": e.errors()}), 400
    except Exception as e:
        print(f"Error in /recommend-jobs: {e}")
        return jsonify({"error": "An internal server error occurred."}), 500

@recommendation_bp.route('/rank-pending-candidates', methods=['POST'])
@require_api_key
def rank_pending_candidates():
    """
    API endpoint để xếp hạng các ứng viên đã apply.
    """
    try:
        # Validate dữ liệu đầu vào
        data = RankCandidatesRequestDTO(**request.json)
        # Gọi service để lấy kết quả
        results = RecommendationService.rank_pending_candidates(data)
        # Đóng gói response theo DTO
        response_dto = RecommendationResponseDTO(results=results)
        return jsonify(response_dto.dict()), 200
    except ValidationError as e:
        return jsonify({"error": "Invalid input data", "details": e.errors()}), 400
    except Exception as e:
        print(f"Error in /rank-pending-candidates: {e}")
        return jsonify({"error": "An internal server error occurred."}), 500
