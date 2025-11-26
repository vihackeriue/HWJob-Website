package com.hw.hwjobbackend.controller.candidate;


import com.hw.hwjobbackend.model.dto.request.application.ApplicationRequest;
import com.hw.hwjobbackend.model.dto.response.ApiResponse;
import com.hw.hwjobbackend.model.dto.response.application.ApplicationResponse;
import com.hw.hwjobbackend.service.candidate.application.CandidateApplicationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/candidate/applications")
public class CandidateApplicationController {
    CandidateApplicationService candidateApplicationService;

    @PostMapping("/apply-job")
    ApiResponse<ApplicationResponse> apply(
            @RequestBody ApplicationRequest request
    ) {
        return ApiResponse.<ApplicationResponse>builder()
                .result(candidateApplicationService.applyJob(request))
                .build();
    }
}
