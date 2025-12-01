package com.hw.hwjobbackend.service.admin.skill;

import com.hw.hwjobbackend.model.dto.request.skill.SkillRequest;
import com.hw.hwjobbackend.model.dto.response.skill.SkillResponse;
import com.hw.hwjobbackend.model.enums.data.SkillEnum;

public interface AdminSkillService {

    SkillResponse createSkill(SkillRequest request);

    SkillResponse updateSkill(Long id, SkillRequest request);

    void deleteSkill(Long id);

}
