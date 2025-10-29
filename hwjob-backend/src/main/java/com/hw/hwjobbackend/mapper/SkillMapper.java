package com.hw.hwjobbackend.mapper;

import com.hw.hwjobbackend.dto.request.SkillRequest;
import com.hw.hwjobbackend.dto.response.SkillResponse;
import com.hw.hwjobbackend.entity.Skill;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SkillMapper {
    SkillResponse toSkillResponse(Skill skill);

    Skill toSkill(SkillRequest request);
}
