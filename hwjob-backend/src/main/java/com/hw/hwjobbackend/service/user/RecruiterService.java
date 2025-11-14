package com.hw.hwjobbackend.service.user;

import com.hw.hwjobbackend.dto.request.user.RecruiterUpdateRequest;
import com.hw.hwjobbackend.dto.response.user.RecruiterResponse;

public interface RecruiterService {
    RecruiterResponse updateRecruiterInfo(RecruiterUpdateRequest request);

}
