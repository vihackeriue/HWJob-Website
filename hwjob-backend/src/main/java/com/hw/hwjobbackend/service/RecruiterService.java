package com.hw.hwjobbackend.service;

import com.hw.hwjobbackend.dto.request.RecruiterUpdateRequest;
import com.hw.hwjobbackend.dto.response.RecruiterResponse;

public interface RecruiterService {
    RecruiterResponse updateRecruiterInfo(String recruiterId, RecruiterUpdateRequest request);

}
