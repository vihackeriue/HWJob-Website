package com.hw.hwjobbackend.service.user.implement;

import com.hw.hwjobbackend.dto.request.user.CandidateUpdateRequest;
import com.hw.hwjobbackend.dto.response.user.CandidateResponse;
import com.hw.hwjobbackend.entity.Candidate;
import com.hw.hwjobbackend.exception.AppException;
import com.hw.hwjobbackend.exception.ErrorCode;
import com.hw.hwjobbackend.mapper.user.CandidateMapper;

import com.hw.hwjobbackend.repository.user.CandidateRepository;
import com.hw.hwjobbackend.service.user.CandidateService;
import com.hw.hwjobbackend.service.user.UserService;
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
public class CandidateServiceImpl implements CandidateService {

    CandidateRepository candidateRepository;
    CandidateMapper candidateMapper;
    UserService userService;

    @Override
    @Transactional
    @PreAuthorize("hasRole('CANDIDATE') or hasRole('ADMIN') ")
    public CandidateResponse updateCandidateInfo(CandidateUpdateRequest request) {
        SecurityContext context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();

        Candidate candidate = getCandidateEntityByName(username);

        candidateMapper.updateCandidate(candidate, request);

        userService.updatePassword(candidate, request.getPassword());
        userService.updateRegion(candidate, request);

        Candidate savedCandidate = candidateRepository.save(candidate);

        return candidateMapper.toCandidateResponse(savedCandidate);
    }

    @Override
    public Candidate getCandidateEntityByName(String username) {
        return candidateRepository.findByUsername(username).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED)
        );
    }


}
