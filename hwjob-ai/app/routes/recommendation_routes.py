from flask import Blueprint, request, jsonify
from pydantic import ValidationError
from app.schemas.request.recommend_jobs_request_dto import RecommendJobsRequestDTO
from app.schemas.request.rank_candidates_request_dto import RankCandidatesRequestDTO
from app.schemas.response.recommendation_response_dto import RecommendationResponseDTO
from app.services.recommendation_service import RecommendationService

# Tạo một Blueprint cho các route liên quan đến gợi ý
recommendation_bp = Blueprint('recommendation_bp', __name__)

@recommendation_bp.route('/recommend-jobs', methods=['POST'])
def recommend_jobs():
    """
    API endpoint để gợi ý việc làm cho ứng viên.
    """
    try:
        # Validate dữ liệu đầu vào
        data = RecommendJobsRequestDTO(**request.json)
        # Gọi service để lấy kết quả
        results = RecommendationService.recommend_jobs(data)
        # Đóng gói response theo DTO
        response_dto = RecommendationResponseDTO(results=results)
        return jsonify(response_dto.dict()), 200
    except ValidationError as e:
        return jsonify({"error": "Invalid input data", "details": e.errors()}), 400
    except Exception as e:
        print(f"Error in /recommend-jobs: {e}")
        return jsonify({"error": "An internal server error occurred."}), 500

@recommendation_bp.route('/rank-pending-candidates', methods=['POST'])
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
