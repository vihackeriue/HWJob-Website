package com.hw.hwjobbackend.service.implement;

import com.hw.hwjobbackend.dto.request.SkillRequest;
import com.hw.hwjobbackend.dto.response.SkillResponse;
import com.hw.hwjobbackend.entity.Skill;
import com.hw.hwjobbackend.exception.AppException;
import com.hw.hwjobbackend.exception.ErrorCode;
import com.hw.hwjobbackend.mapper.SkillMapper;
import com.hw.hwjobbackend.repository.SkillRepository;
import com.hw.hwjobbackend.service.SkillService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class SkillServiceImpl implements SkillService {

    SkillRepository skillRepository;
    SkillMapper skillMapper;


    @Override
    public List<SkillResponse> getAllSkill() {
        return skillRepository.findAll().stream().map(
                skillMapper::toSkillResponse).toList();
    }

    @Override
    public SkillResponse getSkillById(Long id) {
        return skillRepository.findById(id).map(skillMapper::toSkillResponse)
                .orElseThrow(() -> new AppException(ErrorCode.SKILL_NOT_EXISTED));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public SkillResponse createSkill(SkillRequest request) {
        if (skillRepository.existsByName((request.getName()))) {
            throw new AppException(ErrorCode.Skill_EXISTED);
        }
        Skill skill = skillMapper.toSkill(request);
        skill = skillRepository.save(skill);
        return skillMapper.toSkillResponse(skill);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public SkillResponse updateSkill(Long id, SkillRequest request) {
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SKILL_NOT_EXISTED));
        skill.setName(request.getName());
        skill = skillRepository.save(skill);
        return skillMapper.toSkillResponse(skill);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteSkill(Long id) {
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SKILL_NOT_EXISTED));
        skillRepository.delete(skill);
    }
}
