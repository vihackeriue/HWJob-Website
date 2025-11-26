package com.hw.hwjobbackend.controller.candidate;


import com.hw.hwjobbackend.model.dto.request.user.CandidateUpdateRequest;
import com.hw.hwjobbackend.model.dto.response.ApiResponse;
import com.hw.hwjobbackend.model.dto.response.user.CandidateResponse;
import com.hw.hwjobbackend.service.candidate.candidate_user.CandidateUserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/candidate/users")
public class CandidateUserController {

    CandidateUserService candidateUserService;

    @PutMapping
    ApiResponse<CandidateResponse> updateCandidate(
            @RequestBody @Valid CandidateUpdateRequest request) {
        return ApiResponse.<CandidateResponse>builder()
                .result(candidateUserService.updateCandidateInfo(request))
                .build();
    }

}
