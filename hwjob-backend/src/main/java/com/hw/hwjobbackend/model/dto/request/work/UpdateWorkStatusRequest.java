package com.hw.hwjobbackend.model.dto.request.work;

import com.hw.hwjobbackend.model.enums.WorkStatusEnum;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateWorkStatusRequest {
     WorkStatusEnum status;
     String submission;
}
