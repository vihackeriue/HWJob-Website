package com.hw.hwjobbackend.model.entity.job_post;

import com.hw.hwjobbackend.model.entity.user.Recruiter;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "job_posts_boost_history")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class JobPostBoostHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    JobPost jobPost;

    @ManyToOne
    Recruiter recruiter;

    Integer boostDays;

    Long cost;

    @CreatedDate
    @Column(updatable = false)
    LocalDateTime createdAt;
}
