package com.hw.hwjobbackend.controller.recruiter;


import com.hw.hwjobbackend.model.dto.request.user.RecruiterUpdateRequest;
import com.hw.hwjobbackend.model.dto.response.ApiResponse;
import com.hw.hwjobbackend.model.dto.response.user.RecruiterResponse;
import com.hw.hwjobbackend.service.recruiter.recruiter_user.RecruiterUserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/recruiter/users")
public class RecruiterUserController {

    RecruiterUserService recruiterUserService;

    @PutMapping
    ApiResponse<RecruiterResponse> updateRecruiter(
            @RequestBody @Valid RecruiterUpdateRequest request
    ) {
        return ApiResponse.<RecruiterResponse>builder()
                .result(recruiterUserService.updateRecruiterInfo(request))
                .build();
    }
}
