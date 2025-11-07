package com.hw.hwjobbackend.service.level;

import com.hw.hwjobbackend.dto.request.level.LevelRequest;
import com.hw.hwjobbackend.dto.response.level.LevelResponse;

import java.util.List;

public interface LevelService {
    LevelResponse createLevel(LevelRequest request);

    List<LevelResponse> getAllLevels();

    LevelResponse getLevelById(Long id);

    LevelResponse updateLevel(Long id, LevelRequest request);

    void deleteLevel(Long id);
}
