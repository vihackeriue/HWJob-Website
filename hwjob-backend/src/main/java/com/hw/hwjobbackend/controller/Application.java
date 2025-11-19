package com.hw.hwjobbackend.controller;


import com.hw.hwjobbackend.dto.request.application.ApplicationRequest;
import com.hw.hwjobbackend.dto.response.ApiResponse;
import com.hw.hwjobbackend.dto.response.application.ApplicationResponse;
import com.hw.hwjobbackend.service.application.ApplicationService;
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
@RequestMapping("/applications")
public class Application {

    ApplicationService applicationService;

    @PostMapping("/apply")
    ApiResponse<ApplicationResponse> apply(
            @RequestBody ApplicationRequest request
    ) {
        return ApiResponse.<ApplicationResponse>builder()
                .result(applicationService.applyJob(request))
                .build();
    }


}
