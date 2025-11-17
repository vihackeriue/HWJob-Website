package com.hw.hwjobbackend.service.user;

import com.hw.hwjobbackend.dto.request.user.RecruiterUpdateRequest;
import com.hw.hwjobbackend.dto.response.user.RecruiterProfileResponse;
import com.hw.hwjobbackend.dto.response.user.RecruiterResponse;
import com.hw.hwjobbackend.entity.user.Recruiter;

public interface RecruiterService {
    RecruiterResponse updateRecruiterInfo(RecruiterUpdateRequest request);

    Recruiter getRecruiterEntityByName(String name);

    RecruiterProfileResponse getRecruiterProfile(String id);

}
