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
    def _calculate_job_score(semantic_score: float, job_meta: Dict,
                             candidate_profile: RecommendJobsRequestDTO) -> float:
        """Hàm nội bộ tính điểm cho một cặp (Job, Candidate)."""
        job_skills = set([s.strip().lower() for s in str(job_meta.get('skills', '')).split(',') if s.strip()])
        cand_skills = set([s.strip().lower() for s in candidate_profile.skills if s.strip()])

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
        """Gợi ý các công việc phù hợp cho một ứng viên, chỉ bao gồm các job còn hạn và PUBLIC."""
        print("Recommending jobs...")
        skills_str = ", ".join(data.skills)

        semantic_text = f"{data.summary} {data.education} {skills_str}"
        query_text = preprocess_vietnamese_text(semantic_text)
        query_vector = embedding_model.encode(query_text).tolist()

        current_timestamp = int(time.time())
        where_clause = {
            "$and": [
                {"status": {"$eq": "PUBLIC"}},  # Chỉ lấy job có status là PUBLIC
                {"ended_time": {"$gt": current_timestamp}}  # Chỉ lấy job còn hạn
            ]
        }

        n_results = min(50, top_k * 3)
        results = job_collection.query(
            query_embeddings=[query_vector],
            n_results=n_results,
            where=where_clause,  # Áp dụng điều kiện lọc kết hợp
            include=["metadatas", "distances"]
        )

        ranked = []
        if results['ids']:
            for i in range(len(results['ids'][0])):
                final_score = RecommendationService._calculate_job_score(
                    1 - results['distances'][0][i],
                    results['metadatas'][0][i],
                    data
                )
                ranked.append({"id": results['ids'][0][i], "score": final_score})

        ranked.sort(key=lambda x: x['score'], reverse=True)
        print(f"Found and ranked {len(ranked)} public and active jobs.")
        return ranked[:top_k]

    @staticmethod
    def _calculate_candidate_score(semantic_score: float, cand_meta: Dict, job_data: RankCandidatesRequestDTO) -> float:
        """Hàm nội bộ tính điểm cho một cặp (Candidate, Job)."""
        cand_skills = set([s.strip().lower() for s in str(cand_meta.get('skills', '')).split(',') if s.strip()])
        job_skills = set([s.strip().lower() for s in job_data.skills if s.strip()])

        skill_score = 0.0
        if job_skills:
            match_count = len(job_skills.intersection(cand_skills))
            skill_score = match_count / len(job_skills)

        skill_score = max(0.0, min(1.0, skill_score))
        semantic_score = max(0.0, min(1.0, semantic_score))

        weights = get_dynamic_weights(job_data.level)
        return (weights['skill'] * skill_score) + (weights['semantic'] * semantic_score)

    @staticmethod
    def rank_pending_candidates(data: RankCandidatesRequestDTO) -> List[Dict]:
        """Xếp hạng một danh sách các ứng viên đã apply."""
        print(f"Ranking {len(data.pending_candidate_ids)} pending candidates...")
        if not data.pending_candidate_ids:
            return []

        skills_str = ", ".join(data.skills)

        job_semantic_text = f"{data.title} {data.description} {skills_str}"
        job_query_text = preprocess_vietnamese_text(job_semantic_text)
        job_vector = embedding_model.encode(job_query_text)

        pending_ids_str = [str(id) for id in data.pending_candidate_ids]
        candidates_data = candidate_collection.get(ids=pending_ids_str, include=["metadatas", "embeddings"])

        ranked = []
        if candidates_data['ids']:
            for i in range(len(candidates_data['ids'])):
                cand_vector = np.array(candidates_data['embeddings'][i])
                similarity = np.dot(job_vector, cand_vector) / (
                        np.linalg.norm(job_vector) * np.linalg.norm(cand_vector))

                final_score = RecommendationService._calculate_candidate_score(
                    float(similarity),
                    candidates_data['metadatas'][i],
                    data
                )
                ranked.append({"id": candidates_data['ids'][i], "score": final_score})

        ranked.sort(key=lambda x: x['score'], reverse=True)
        print(f"Ranked {len(ranked)} candidates successfully.")
        return ranked
