package com.hw.hwjobbackend.service.shared.skill;

import com.hw.hwjobbackend.model.dto.response.skill.SkillResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SkillService {

    Page<SkillResponse> getAllSkills(Integer page, Integer size);

    List<SkillResponse> getAllSkills();

    SkillResponse getSkillById(Long id);

}
