package com.hw.hwjobbackend.controller.recruiter;


import com.hw.hwjobbackend.model.dto.request.application.ApplicationRequest;
import com.hw.hwjobbackend.model.dto.response.ApiResponse;
import com.hw.hwjobbackend.model.dto.response.application.ApplicationCandidateResponse;
import com.hw.hwjobbackend.service.recruiter.application.RecruiterApplicationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/recruiter/applications")
public class RecruiterApplicationController {

    RecruiterApplicationService recruiterJobPostService;

    @GetMapping("/{id}")
    public ApiResponse<List<ApplicationCandidateResponse>> getCandidatesAppliedJob(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @PathVariable String id
    ) {
        if (page != null && size != null) {
            Page<ApplicationCandidateResponse> response = recruiterJobPostService
                    .getCandidateApplications(page - 1, size, id);
            return ApiResponse.<List<ApplicationCandidateResponse>>builder()
                    .page(response.getNumber() + 1)
                    .totalPages(response.getTotalPages())
                    .result(response.getContent())
                    .build();
        }
        return ApiResponse.<List<ApplicationCandidateResponse>>builder()
                .result(recruiterJobPostService.getAllCandidateApplications(id))
                .build();
    }

    @PutMapping("/update-application")
    public ApiResponse<Void> updateCandidateApplication(
            @RequestBody ApplicationRequest request
    ) {
        recruiterJobPostService.updateCandidateApplication(request);
        return ApiResponse.<Void>builder().build();
    }

}