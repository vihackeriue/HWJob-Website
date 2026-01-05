from flask import Blueprint, request, jsonify
from pydantic import ValidationError
from app.schemas.request.recommend_jobs_request_dto import RecommendJobsRequestDTO
from app.schemas.request.rank_candidates_request_dto import RankCandidatesRequestDTO
from app.schemas.request.search_job_dto import SearchJobRequestDTO
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
    n_results giờ được lấy từ JSON body.
    """
    try:
        # Validate dữ liệu đầu vào từ body, Pydantic sẽ tự gán giá trị mặc định cho n_results
        data = RecommendJobsRequestDTO(**request.json)

        # Gọi service để lấy kết quả, truyền n_results từ DTO vào
        results = RecommendationService.recommend_jobs(data, top_k=data.n_results)

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


@recommendation_bp.route('/search-jobs', methods=['POST'])
@require_api_key
def search_jobs():
    """
    API endpoint để tìm kiếm việc làm theo từ khóa.
    """
    try:
        # Validate dữ liệu đầu vào
        data = SearchJobRequestDTO(**request.json)

        # Gọi service để tìm kiếm
        results = RecommendationService.search_jobs(data)

        # Đóng gói response (tái sử dụng RecommendationResponseDTO vì cấu trúc giống nhau)
        response_dto = RecommendationResponseDTO(results=results)
        return jsonify(response_dto.dict()), 200
    except ValidationError as e:
        return jsonify({"error": "Invalid input data", "details": e.errors()}), 400
    except Exception as e:
        print(f"Error in /search-jobs: {e}")
        return jsonify({"error": "An internal server error occurred."}), 500
