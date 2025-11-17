package com.hw.hwjobbackend.entity.candidate_save_job;

import com.hw.hwjobbackend.entity.JobPost;
import com.hw.hwjobbackend.entity.user.Candidate;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "candidate_save_job")
public class CandidateSaveJob {

    @EmbeddedId
    CandidateSaveJobId id;

    @ManyToOne
    @MapsId("candidateId")
    Candidate candidate;

    @ManyToOne
    @MapsId("jobPostId")
    JobPost jobPost;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    Date createdAt;

}
