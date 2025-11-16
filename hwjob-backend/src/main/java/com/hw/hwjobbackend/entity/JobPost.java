package com.hw.hwjobbackend.entity;

import com.hw.hwjobbackend.enums.JobPostStatus;
import com.hw.hwjobbackend.enums.SalaryType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.Date;

@Entity(name = "job-posts")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JobPost {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    String title;

    @Column(columnDefinition = "TEXT")
    String description;

    Integer quantity;

    Long salaryMin;

    SalaryType salaryType;

    JobPostStatus postStatus;

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

//    @ManyToOne(fetch = FetchType.LAZY)
//    Ward ward;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    Date createdAt;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    Date updatedAt;

}
