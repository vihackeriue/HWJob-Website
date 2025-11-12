package com.hw.hwjobbackend.mapper;


import com.hw.hwjobbackend.dto.request.level.LevelRequest;
import com.hw.hwjobbackend.dto.response.level.LevelResponse;
import com.hw.hwjobbackend.entity.Level;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LevelMapper {
    Level toLevel(LevelRequest request);

    LevelResponse toLevelResponse(Level level);
}
