package com.hw.hwjobbackend.service.admin.skill;


import com.hw.hwjobbackend.exception.AppException;
import com.hw.hwjobbackend.exception.ErrorCode;
import com.hw.hwjobbackend.model.dto.request.skill.SkillRequest;
import com.hw.hwjobbackend.model.dto.response.skill.SkillResponse;
import com.hw.hwjobbackend.model.entity.skill.Skill;
import com.hw.hwjobbackend.repository.skill.SkillRepository;
import com.hw.hwjobbackend.service.mapper.skill.SkillMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AdminSkillServiceImpl implements AdminSkillService {

    SkillRepository skillRepository;
    SkillMapper skillMapper;

    @Override
    public SkillResponse createSkill(SkillRequest request) {

        validateSkillNameNotExists(request.getName(), null);
        Skill skill = skillMapper.toSkill(request);
        skill = skillRepository.save(skill);

        return skillMapper.toSkillResponse(skill);
    }

    @Override
    public SkillResponse updateSkill(Long id, SkillRequest request) {
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SKILL_NOT_EXISTED));
        validateSkillNameNotExists(request.getName(), id);

        if (hasNoChanges(skill, request)) {
            return skillMapper.toSkillResponse(skill);
        }
        skillMapper.updateSkill(request, skill);
        return skillMapper.toSkillResponse(skill);
    }

    @Override
    public void deleteSkill(Long id) {
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SKILL_NOT_EXISTED));
        skillRepository.delete(skill);
    }

    private void validateSkillNameNotExists(String name, Long excludeId) {
        boolean exists = (excludeId == null)
                ? skillRepository.existsByNameIgnoreCase(name)
                : skillRepository.existsByNameIgnoreCaseAndIdNot(name, excludeId);
        if (exists) {
            throw new AppException(ErrorCode.SKILL_EXISTED);
        }
    }

    private boolean hasNoChanges(Skill skill, SkillRequest request) {
        return Objects.equals(skill.getName(), request.getName());
    }
}
