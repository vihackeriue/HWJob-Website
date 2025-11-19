package com.hw.hwjobbackend.entity.application;


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

    String candidateId;

    String jobPostId;

}
