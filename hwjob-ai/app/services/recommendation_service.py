import numpy as np
import time
from app.core.ai_core import embedding_model, job_collection, candidate_collection
from app.utils.text_processing import preprocess_vietnamese_text
from app.utils.scoring import get_dynamic_weights
from app.schemas.request.recommend_jobs_request_dto import RecommendJobsRequestDTO
from app.schemas.request.rank_candidates_request_dto import RankCandidatesRequestDTO
from typing import List, Dict

class RecommendationService:
    """
    Lớp dịch vụ xử lý các tác vụ gợi ý và xếp hạng.
    """
    @staticmethod
    def _calculate_job_score(semantic_score: float, job_meta: Dict, candidate_meta: Dict) -> float:
        """Hàm nội bộ tính điểm cho một cặp (Job, Candidate)."""
        job_skills = set([s.strip().lower() for s in str(job_meta.get('skills', '')).split(',') if s.strip()])
        cand_skills = set([s.strip().lower() for s in str(candidate_meta.get('skills', '')).split(',') if s.strip()])
        
        skill_score = 0.0
        if job_skills:
            match_count = len(job_skills.intersection(cand_skills))
            skill_score = match_count / len(job_skills)

        skill_score = max(0.0, min(1.0, skill_score))
        semantic_score = max(0.0, min(1.0, semantic_score))

        weights = get_dynamic_weights(job_meta.get('level', 'Junior'))
        return (weights['skill'] * skill_score) + (weights['semantic'] * semantic_score)

    @staticmethod
    def recommend_jobs(data: RecommendJobsRequestDTO, top_k: int = 10) -> List[Dict]:
        """
        [LOGIC MỚI] Gợi ý việc làm dựa trên ID của ứng viên.
        """
        print(f"Recommending jobs for candidate_id: {data.candidate_id}...")
        
        # 1. Lấy vector và metadata của ứng viên từ ChromaDB
        candidate_data = candidate_collection.get(ids=[data.candidate_id], include=["embeddings", "metadatas"])
        if not candidate_data or not candidate_data['ids']:
            print(f"Warning: Candidate with ID {data.candidate_id} not found in vector store.")
            return []
            
        query_vector = candidate_data['embeddings'][0]
        candidate_meta = candidate_data['metadatas'][0]

        # 2. Tạo điều kiện lọc (còn hạn và public)
        current_timestamp = int(time.time())
        where_clause = {
            "$and": [
                {"status": {"$eq": "PUBLIC"}},
                {"ended_time": {"$gt": current_timestamp}}
            ]
        }
        
        # 3. Truy vấn ChromaDB để lấy các job tương đồng
        n_results = min(50, top_k * 3)
        results = job_collection.query(
            query_embeddings=[query_vector], 
            n_results=n_results, 
            where=where_clause,
            include=["metadatas", "distances"]
        )
        
        # 4. Re-rank các kết quả
        ranked = []
        if results['ids']:
            for i in range(len(results['ids'][0])):
                final_score = RecommendationService._calculate_job_score(
                    1 - results['distances'][0][i], 
                    results['metadatas'][0][i], 
                    candidate_meta # Sử dụng metadata của candidate đã lấy ở bước 1
                )
                ranked.append({"id": results['ids'][0][i], "score": final_score})
            
        ranked.sort(key=lambda x: x['score'], reverse=True)
        print(f"Found and ranked {len(ranked)} jobs.")
        return ranked[:top_k]

    @staticmethod
    def _calculate_candidate_score(semantic_score: float, cand_meta: Dict, job_meta: Dict) -> float:
        """Hàm nội bộ tính điểm cho một cặp (Candidate, Job)."""
        cand_skills = set([s.strip().lower() for s in str(cand_meta.get('skills', '')).split(',') if s.strip()])
        job_skills = set([s.strip().lower() for s in str(job_meta.get('skills', '')).split(',') if s.strip()])
        
        skill_score = 0.0
        if job_skills:
            match_count = len(job_skills.intersection(cand_skills))
            skill_score = match_count / len(job_skills)
        
        skill_score = max(0.0, min(1.0, skill_score))
        semantic_score = max(0.0, min(1.0, semantic_score))

        weights = get_dynamic_weights(job_meta.get('level', 'Junior'))
        return (weights['skill'] * skill_score) + (weights['semantic'] * semantic_score)

    @staticmethod
    def rank_pending_candidates(data: RankCandidatesRequestDTO) -> List[Dict]:
        """
        [LOGIC MỚI] Xếp hạng các ứng viên đã apply dựa trên ID của Job.
        """
        print(f"Ranking {len(data.pending_candidate_ids)} candidates for job_id: {data.job_id}...")
        if not data.pending_candidate_ids:
            return []

        # 1. Lấy vector và metadata của Job Post từ ChromaDB
        job_data = job_collection.get(ids=[data.job_id], include=["embeddings", "metadatas"])
        if not job_data or not job_data['ids']:
            print(f"Warning: Job with ID {data.job_id} not found in vector store.")
            return []
            
        job_vector = np.array(job_data['embeddings'][0])
        job_meta = job_data['metadatas'][0]

        # 2. Lấy thông tin của các ứng viên pending từ ChromaDB
        candidates_data = candidate_collection.get(ids=data.pending_candidate_ids, include=["metadatas", "embeddings"])

        # 3. Chấm điểm và sắp xếp
        ranked = []
        if candidates_data['ids']:
            for i in range(len(candidates_data['ids'])):
                cand_vector = np.array(candidates_data['embeddings'][i])
                similarity = np.dot(job_vector, cand_vector) / (np.linalg.norm(job_vector) * np.linalg.norm(cand_vector))
                
                final_score = RecommendationService._calculate_candidate_score(
                    float(similarity), 
                    candidates_data['metadatas'][i], 
                    job_meta # Sử dụng metadata của job đã lấy ở bước 1
                )
                ranked.append({"id": candidates_data['ids'][i], "score": final_score})

        ranked.sort(key=lambda x: x['score'], reverse=True)
        print(f"Ranked {len(ranked)} candidates successfully.")
        return ranked
