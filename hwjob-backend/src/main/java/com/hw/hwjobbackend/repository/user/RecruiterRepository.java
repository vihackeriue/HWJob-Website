package com.hw.hwjobbackend.repository.user;

import com.hw.hwjobbackend.model.dto.response.user.RecruiterHomeResponse;
import com.hw.hwjobbackend.model.entity.user.Recruiter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecruiterRepository extends JpaRepository<Recruiter, String> {

    @Query("""
                SELECT new com.hw.hwjobbackend.model.dto.response.user.RecruiterHomeResponse(
                    r.id,
                    r.username,
                    r.fullName,
                    r.imageUrl,
                    COUNT(jp.id),
                    0
                )
                FROM Recruiter r
                LEFT JOIN JobPost jp ON jp.recruiter = r
                GROUP BY r.id, r.username, r.fullName, r.imageUrl, r.createdAt
                ORDER BY r.createdAt DESC
            """)
    List<RecruiterHomeResponse> findTopRecruiters(Pageable pageable);

}
