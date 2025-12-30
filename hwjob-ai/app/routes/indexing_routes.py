from flask import Blueprint, request, jsonify
from pydantic import ValidationError
from app.schemas.request.job_index_dto import JobIndexDTO
from app.schemas.request.candidate_index_dto import CandidateIndexDTO
from app.services.indexing_service import IndexingService

# Tạo một Blueprint cho các route liên quan đến indexing
indexing_bp = Blueprint('indexing_bp', __name__)

@indexing_bp.route('/index-job', methods=['POST'])
def index_job():
    """
    API endpoint để index một Job Post.
    Nhận JSON từ Spring Boot, validate và gọi service.
    """
    try:
        # Validate dữ liệu đầu vào bằng Pydantic DTO
        data = JobIndexDTO(**request.json)
        # Gọi service để xử lý logic
        IndexingService.index_job(data)
        return jsonify({"message": f"Job {data.job_id} indexed successfully."}), 200
    except ValidationError as e:
        # Lỗi validation
        return jsonify({"error": "Invalid input data", "details": e.errors()}), 400
    except Exception as e:
        # Các lỗi khác
        print(f"Error in /index-job: {e}")
        return jsonify({"error": "An internal server error occurred."}), 500

@indexing_bp.route('/index-candidate', methods=['POST'])
def index_candidate():
    """
    API endpoint để index một Candidate.
    Nhận JSON từ Spring Boot, validate và gọi service.
    """
    try:
        # Validate dữ liệu đầu vào
        data = CandidateIndexDTO(**request.json)
        # Gọi service
        IndexingService.index_candidate(data)
        return jsonify({"message": f"Candidate {data.candidate_id} indexed successfully."}), 200
    except ValidationError as e:
        return jsonify({"error": "Invalid input data", "details": e.errors()}), 400
    except Exception as e:
        print(f"Error in /index-candidate: {e}")
        return jsonify({"error": "An internal server error occurred."}), 500
