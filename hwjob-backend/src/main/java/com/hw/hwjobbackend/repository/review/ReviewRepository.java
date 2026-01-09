package com.hw.hwjobbackend.repository.review;

import com.hw.hwjobbackend.model.entity.review.Review;


import feign.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {


    boolean existsByWork_IdAndReviewer_Id(String workId, String reviewerId);

    List<Review> findByRevieweeId(String revieweeId);
    Page<Review> findByRevieweeId(String revieweeId, Pageable pageable);

    @Query("""
        SELECT AVG(r.rating)
        FROM Review r
        WHERE r.reviewee.id = :userId
    """)
    Double getAverageRating(@Param("userId") String userId);


    @Query("""
        SELECT r.rating
        FROM Review r
        WHERE r.work.id = :workId
          AND r.reviewer.id = :reviewerId
    """)
    Double findMyRating(
            @Param("workId") String workId,
            @Param("reviewerId") String reviewerId
    );

    @Query("SELECT AVG(r.rating) " +
            "FROM Review r " +
            "WHERE r.work.jobPost.id = :jobPostId ")
    Double findAverageRatingByJobPost(@Param("jobPostId") String jobPostId);

    @Query("""
        SELECT r.reviewee.id, AVG(r.rating)
        FROM Review r
        WHERE r.reviewee.id IN :userIds
        GROUP BY r.reviewee.id
    """)
    List<Object[]> getAverageRatingByUserIds(@Param("userIds") List<String> userIds);
}
