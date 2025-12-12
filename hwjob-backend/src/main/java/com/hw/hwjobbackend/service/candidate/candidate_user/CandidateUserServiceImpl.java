package com.hw.hwjobbackend.service.candidate.candidate_user;

import com.hw.hwjobbackend.exception.AppException;
import com.hw.hwjobbackend.exception.ErrorCode;
import com.hw.hwjobbackend.model.entity.skill.Skill;
import com.hw.hwjobbackend.service.mapper.user.CandidateMapper;
import com.hw.hwjobbackend.model.dto.request.user.CandidateUpdateRequest;
import com.hw.hwjobbackend.model.dto.response.user.CandidateResponse;
import com.hw.hwjobbackend.model.entity.user.Candidate;
import com.hw.hwjobbackend.repository.user.CandidateRepository;
import com.hw.hwjobbackend.service.shared.skill.SkillService;
import com.hw.hwjobbackend.service.shared.user.UserService;
import com.hw.hwjobbackend.util.SecurityUtils;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CandidateUserServiceImpl implements CandidateUserService {
    CandidateRepository candidateRepository;
    CandidateMapper candidateMapper;
    UserService userService;
    SkillService skillService;

    @Override
    @Transactional
    public CandidateResponse updateCandidateInfo(CandidateUpdateRequest request) {

        String userId = SecurityUtils.getCurrentUserId();

        Candidate candidate = candidateRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        userService.validateExistEmail(candidate, request.getEmail());

        userService.validateRegion(candidate, request.getRegionId());

        if (request.getSkillIds() != null) {
            Set<Skill> skills = skillService.getSkillsByIds(request.getSkillIds());
            candidate.setSkills(skills);
        }

        candidateMapper.updateCandidate(candidate, request);

        candidateRepository.save(candidate);

        return candidateMapper.toCandidateResponse(candidate);
    }
}