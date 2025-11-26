package com.hw.hwjobbackend.service.candidate.candidate_user;


import com.hw.hwjobbackend.exception.AppException;
import com.hw.hwjobbackend.exception.ErrorCode;
import com.hw.hwjobbackend.mapper.user.CandidateMapper;
import com.hw.hwjobbackend.model.dto.request.user.CandidateUpdateRequest;
import com.hw.hwjobbackend.model.dto.response.user.CandidateResponse;
import com.hw.hwjobbackend.model.entity.user.Candidate;
import com.hw.hwjobbackend.repository.user.CandidateRepository;
import com.hw.hwjobbackend.service.shared.user.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        SecurityContext context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();

        Candidate candidate = candidateRepository.findByUsername(username).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED)
        );

        candidateMapper.updateCandidate(candidate, request);

        userService.updatePassword(candidate, request.getPassword());
        userService.updateRegion(candidate, request);

        Candidate savedCandidate = candidateRepository.save(candidate);

        return candidateMapper.toCandidateResponse(savedCandidate);
    }

}
