package com.hw.hwjobbackend.model.entity.job_post;

import com.hw.hwjobbackend.model.entity.job_type.JobType;
import com.hw.hwjobbackend.model.entity.level.Level;
import com.hw.hwjobbackend.model.entity.industry.Industry;
import com.hw.hwjobbackend.model.entity.region.Region;
import com.hw.hwjobbackend.model.entity.skill.Skill;
import com.hw.hwjobbackend.model.entity.user.Recruiter;
import com.hw.hwjobbackend.model.enums.CandidateGenderEnum;
import com.hw.hwjobbackend.model.enums.JobPostStatusEnum;
import com.hw.hwjobbackend.model.enums.SalaryTypeEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "job_posts")
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
    String description;

    Integer quantity;

    Long salary;

    @Enumerated(EnumType.STRING)
    SalaryTypeEnum salaryType;

    @Enumerated(EnumType.STRING)
    JobPostStatusEnum status;

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
    Region region;

    @ManyToMany(fetch = FetchType.LAZY)
    Set<Skill> skills;

    @CreatedDate
    @Column(updatable = false)
    LocalDateTime createdAt;

    @LastModifiedDate
    LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        if (status == null) {
            status = JobPostStatusEnum.PUBLIC;
        }
    }


}
