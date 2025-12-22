package com.hw.hwjobbackend.model.entity.works;

import com.hw.hwjobbackend.model.entity.application.Application;
import com.hw.hwjobbackend.model.entity.job_post.JobPost;
import com.hw.hwjobbackend.model.entity.user.Candidate;
import com.hw.hwjobbackend.model.entity.user.Recruiter;
import com.hw.hwjobbackend.model.enums.SalaryTypeEnum;
import com.hw.hwjobbackend.model.enums.WorkStatusEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "works")
public class Work {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    // Candidate được nhận job
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id", nullable = false)
    Candidate candidate;

    // Recruiter đăng job
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruiter_id", nullable = false)
    Recruiter recruiter;

    // Job gốc
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_post_id", nullable = false)
    JobPost jobPost;

    // Application được accept
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "candidate_id", referencedColumnName = "candidate_id", insertable = false, updatable = false),
            @JoinColumn(name = "job_post_id", referencedColumnName = "job_post_id", insertable = false, updatable = false)
    })
    Application application;

    // Lương chốt (snapshot, phòng job bị sửa)
    Long agreedSalary;

    @Enumerated(EnumType.STRING)
    SalaryTypeEnum salaryType;

    @Enumerated(EnumType.STRING)
    WorkStatusEnum status;

    LocalDateTime startTime;
    LocalDateTime endTime;

    @CreatedDate
    @Column(updatable = false)
    LocalDateTime createdAt;

    @LastModifiedDate
    LocalDateTime updatedAt;
}
