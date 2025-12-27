package com.hw.hwjobbackend.model.entity.application;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Embeddable
public class ApplicationId implements Serializable {
    @Column(name = "candidate_id")
    String candidateId;
    @Column(name = "job_post_id")
    String jobPostId;

}
