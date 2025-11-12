package com.hw.hwjobbackend.service.user.implement;

import com.hw.hwjobbackend.dto.request.user.CandidateUpdateRequest;
import com.hw.hwjobbackend.dto.response.user.CandidateResponse;
import com.hw.hwjobbackend.entity.Skill;
import com.hw.hwjobbackend.entity.Candidate;
import com.hw.hwjobbackend.exception.AppException;
import com.hw.hwjobbackend.exception.ErrorCode;
import com.hw.hwjobbackend.mapper.CandidateMapper;
import com.hw.hwjobbackend.repository.*;

import com.hw.hwjobbackend.service.user.CandidateService;
import com.hw.hwjobbackend.service.user.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.Set;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CandidateServiceImpl implements CandidateService {

    CandidateRepository candidateRepository;
    SkillRepository skillRepository;
    CandidateMapper candidateMapper;
    UserService userService;

    @Override
    @Transactional
    @PreAuthorize("hasRole('CANDIDATE') or hasRole('ADMIN') ")
    public CandidateResponse updateCandidateInfo(String candidateId, CandidateUpdateRequest request) {

        Candidate candidate = candidateRepository.findById(candidateId)
                .filter(Candidate.class::isInstance)
                .map(Candidate.class::cast)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        candidateMapper.updateCandidate(candidate, request);

        userService.updatePassword(candidate, request.getPassword());
        userService.updateLocation(candidate, request);
        updateSkills(candidate, request.getSkillIds());

        Candidate savedCandidate = candidateRepository.save(candidate);

        return candidateMapper.toCandidateResponse(savedCandidate);
    }

    private void updateSkills(Candidate candidate, Set<Long> skillIds) {
        if (!CollectionUtils.isEmpty(skillIds)) {
            Set<Skill> skills = new HashSet<>(skillRepository.findAllById(skillIds));
            if (skills.size() != skillIds.size()) {
                throw new AppException(ErrorCode.SKILL_NOT_EXISTED);
            }
            candidate.setSkills(skills);
        }
    }
}
