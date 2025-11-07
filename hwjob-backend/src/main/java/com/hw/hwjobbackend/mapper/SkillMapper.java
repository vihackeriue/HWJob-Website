package com.hw.hwjobbackend.mapper;

import com.hw.hwjobbackend.dto.request.skill.SkillRequest;
import com.hw.hwjobbackend.dto.response.skill.SkillResponse;
import com.hw.hwjobbackend.entity.Skill;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SkillMapper {
    SkillResponse toSkillResponse(Skill skill);

    Skill toSkill(SkillRequest request);
}
