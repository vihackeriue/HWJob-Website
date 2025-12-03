package com.hw.hwjobbackend.service.admin.level;

import com.hw.hwjobbackend.model.dto.request.level.LevelRequest;
import com.hw.hwjobbackend.model.dto.response.level.LevelResponse;

public interface AdminLevelService {

    LevelResponse createLevel(LevelRequest request);

    LevelResponse updateLevel(Long id, LevelRequest request);

    void deleteLevel(Long id);
}
