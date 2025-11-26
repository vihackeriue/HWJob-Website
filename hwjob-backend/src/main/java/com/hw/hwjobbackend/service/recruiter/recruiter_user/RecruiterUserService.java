package com.hw.hwjobbackend.service.recruiter.recruiter_user;

import com.hw.hwjobbackend.model.dto.request.user.RecruiterUpdateRequest;
import com.hw.hwjobbackend.model.dto.response.user.RecruiterResponse;

public interface RecruiterUserService {
    RecruiterResponse updateRecruiterInfo(RecruiterUpdateRequest request);
}
