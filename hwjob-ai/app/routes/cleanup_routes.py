from flask import Blueprint, jsonify
from app.services.cleanup_service import CleanupService
from app.core.security import require_api_key

# Tạo một Blueprint cho các route liên quan đến dọn dẹp dữ liệu
cleanup_bp = Blueprint('cleanup_bp', __name__)


@cleanup_bp.route('/delete-job/<string:job_id>', methods=['DELETE'])
@require_api_key
def delete_job(job_id: str):
    """
    API endpoint để xóa một Job Post khỏi ChromaDB.
    Route này chỉ điều phối, logic xử lý nằm ở CleanupService.
    """
    if not job_id:
        return jsonify({"error": "Job ID is required."}), 400

    try:
        # Gọi service để thực hiện việc xóa
        CleanupService.delete_job(job_id)
        return jsonify({"message": f"Deletion processed for job {job_id}."}), 200
    except Exception as e:
        print(f"Error in /delete-job: {e}")
        return jsonify({"error": "An internal server error occurred."}), 500


@cleanup_bp.route('/delete-candidate/<string:candidate_id>', methods=['DELETE'])
@require_api_key
def delete_candidate(candidate_id: str):
    """
    API endpoint để xóa một Candidate khỏi ChromaDB.
    Route này chỉ điều phối, logic xử lý nằm ở CleanupService.
    """
    if not candidate_id:
        return jsonify({"error": "Candidate ID is required."}), 400

    try:
        # Gọi service để thực hiện việc xóa
        CleanupService.delete_candidate(candidate_id)
        return jsonify({"message": f"Deletion processed for candidate {candidate_id}."}), 200
    except Exception as e:
        print(f"Error in /delete-candidate: {e}")
        return jsonify({"error": "An internal server error occurred."}), 500
