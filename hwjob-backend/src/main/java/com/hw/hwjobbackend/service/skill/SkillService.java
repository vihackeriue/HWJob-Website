package com.hw.hwjobbackend.service.skill;

import com.hw.hwjobbackend.dto.request.skill.SkillRequest;
import com.hw.hwjobbackend.dto.response.skill.SkillResponse;

import java.util.List;

public interface SkillService {
    // get all
    List<SkillResponse> getAllSkill();

    // get by id
    SkillResponse getSkillById(Long id);

    // create
    SkillResponse createSkill(SkillRequest request);

    //update
    SkillResponse updateSkill(Long id, SkillRequest request);

    //delete
    void deleteSkill(Long id);
}
