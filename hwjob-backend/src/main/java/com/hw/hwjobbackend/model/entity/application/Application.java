package com.hw.hwjobbackend.model.entity.application;


import com.hw.hwjobbackend.model.entity.job_post.JobPost;
import com.hw.hwjobbackend.model.entity.user.Candidate;
import com.hw.hwjobbackend.model.enums.ApplicationStatusEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Date;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "applications")
@EntityListeners(AuditingEntityListener.class)
public class Application {

    @EmbeddedId
    ApplicationId id;

    @ManyToOne
    @MapsId("candidateId")
    Candidate candidate;

    @ManyToOne
    @MapsId("jobPostId")
    JobPost jobPost;

    @Enumerated(EnumType.STRING)
    ApplicationStatusEnum status;

    Double rating;

    @Lob
    String feedback;

    @CreatedDate
    @Column(updatable = false)
    LocalDateTime createdAt;

    @LastModifiedDate
    LocalDateTime updatedAt;


    @PrePersist
    public void prePersist() {
        if (status == null) {
            status = ApplicationStatusEnum.PENDING;
        }
    }

}

