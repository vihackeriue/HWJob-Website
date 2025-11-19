package com.hw.hwjobbackend.entity.candidate_save_job;


import jakarta.persistence.Embeddable;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Embeddable
public class CandidateSaveJobId {
    String candidateId;

    String jobPostId;
}
