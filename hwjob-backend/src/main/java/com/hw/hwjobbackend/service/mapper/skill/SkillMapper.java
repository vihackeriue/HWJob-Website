package com.hw.hwjobbackend.service.mapper.skill;


import com.hw.hwjobbackend.model.dto.request.skill.SkillRequest;
import com.hw.hwjobbackend.model.dto.response.skill.SkillResponse;
import com.hw.hwjobbackend.model.entity.skill.Skill;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface SkillMapper {

    Skill toSkill(SkillRequest request);

    SkillResponse toSkillResponse(Skill skill);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateSkill(SkillRequest request, @MappingTarget Skill skill);

}
