package com.hw.hwjobbackend.service.skill;

import com.hw.hwjobbackend.dto.request.skill.SkillRequest;
import com.hw.hwjobbackend.dto.response.skill.SkillResponse;

import java.util.List;

public interface SkillService {
    List<SkillResponse> getAllSkill();

    SkillResponse getSkillById(Long id);

    SkillResponse createSkill(SkillRequest request);

    SkillResponse updateSkill(Long id, SkillRequest request);

    void deleteSkill(Long id);
}
