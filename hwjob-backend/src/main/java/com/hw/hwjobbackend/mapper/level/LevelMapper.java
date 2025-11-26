package com.hw.hwjobbackend.mapper.level;


import com.hw.hwjobbackend.model.dto.request.level.LevelRequest;
import com.hw.hwjobbackend.model.dto.response.level.LevelResponse;
import com.hw.hwjobbackend.model.entity.level.Level;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LevelMapper {
    Level toLevel(LevelRequest request);

    LevelResponse toLevelResponse(Level level);
}
