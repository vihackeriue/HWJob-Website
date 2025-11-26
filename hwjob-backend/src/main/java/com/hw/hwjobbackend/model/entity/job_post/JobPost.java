package com.hw.hwjobbackend.model.entity.job_post;

import com.hw.hwjobbackend.model.entity.job_type.JobType;
import com.hw.hwjobbackend.model.entity.level.Level;
import com.hw.hwjobbackend.model.entity.industry.Industry;
import com.hw.hwjobbackend.model.entity.region.Province;
import com.hw.hwjobbackend.model.entity.user.Recruiter;
import com.hw.hwjobbackend.model.enums.JobPostStatus;
import com.hw.hwjobbackend.model.enums.SalaryType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Date;

@Entity(name = "job-posts")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class JobPost {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String title;

    @Lob
    @Column(columnDefinition = "TEXT")
    String description;

    Integer quantity;

    Long salary;

    SalaryType salaryType;

    JobPostStatus status;

    LocalDateTime endedTime;

    @ManyToOne(fetch = FetchType.LAZY)
    Recruiter recruiter;

    @ManyToOne(fetch = FetchType.LAZY)
    Level level;

    @ManyToOne(fetch = FetchType.LAZY)
    JobType jobType;

    @ManyToOne(fetch = FetchType.LAZY)
    Industry industry;

    @ManyToOne(fetch = FetchType.LAZY)
    Province province;


    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    Date createdAt;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    Date updatedAt;

}
