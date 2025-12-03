package com.hw.hwjobbackend.service.mapper.level;


import com.hw.hwjobbackend.model.dto.request.level.LevelRequest;
import com.hw.hwjobbackend.model.dto.response.level.LevelResponse;
import com.hw.hwjobbackend.model.entity.level.Level;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface LevelMapper {
    Level toLevel(LevelRequest request);

    LevelResponse toLevelResponse(Level level);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateLevel(LevelRequest request, @MappingTarget Level level);
}
