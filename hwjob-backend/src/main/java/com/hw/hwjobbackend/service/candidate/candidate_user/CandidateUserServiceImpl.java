package com.hw.hwjobbackend.service.candidate.candidate_user;

import com.hw.hwjobbackend.exception.AppException;
import com.hw.hwjobbackend.exception.ErrorCode;
import com.hw.hwjobbackend.service.mapper.user.CandidateMapper;
import com.hw.hwjobbackend.model.dto.request.user.CandidateUpdateRequest;
import com.hw.hwjobbackend.model.dto.response.user.CandidateResponse;
import com.hw.hwjobbackend.model.entity.user.Candidate;
import com.hw.hwjobbackend.repository.user.CandidateRepository;
import com.hw.hwjobbackend.service.shared.user.UserService;
import com.hw.hwjobbackend.util.SecurityUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@PreAuthorize("hasRole('CANDIDATE')")
public class CandidateUserServiceImpl implements CandidateUserService {
    CandidateRepository candidateRepository;
    CandidateMapper candidateMapper;
    UserService userService;

    @Override
    @Transactional
    public CandidateResponse updateCandidateInfo(CandidateUpdateRequest request) {
        String username = SecurityUtils.getCurrentUsername();

        Candidate candidate = candidateRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        if (request.getEmail() != null && !Objects.equals(candidate.getEmail(), request.getEmail())) {
            userService.validateEmail(candidate.getEmail(), request.getEmail());
        }

        if (request.getRegionId() != null && !Objects.equals(candidate.getRegion().getId(), request.getRegionId())) {
            userService.updateRegion(candidate, request);
        }

        candidateMapper.updateCandidate(candidate, request);

        return candidateMapper.toCandidateResponse(candidate);
    }
}