package com.hw.hwjobbackend.entity.application;


import com.hw.hwjobbackend.entity.JobPost;
import com.hw.hwjobbackend.entity.user.Candidate;
import com.hw.hwjobbackend.enums.ApplicationStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "applications")
public class Application {

    @EmbeddedId
    ApplicationId id;

    @ManyToOne
    @MapsId("candidateId")
    Candidate candidate;

    @ManyToOne
    @MapsId("jobPostId")
    JobPost jobPost;

    ApplicationStatus status;
    Double rating;

    @Lob
    @Column(columnDefinition = "TEXT")
    String feedback;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    Date createdAt;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    Date updatedAt;

}
