package com.hw.hwjobbackend.service.level;

import com.hw.hwjobbackend.model.dto.request.level.LevelRequest;
import com.hw.hwjobbackend.model.dto.response.level.LevelResponse;
import com.hw.hwjobbackend.model.entity.level.Level;
import org.springframework.data.domain.Page;

public interface LevelService {
    LevelResponse createLevel(LevelRequest request);

    Page<LevelResponse> getAllLevels(int page, int size);

    LevelResponse getLevelById(Long id);

    LevelResponse updateLevel(Long id, LevelRequest request);

    void deleteLevel(Long id);

    Level getLevelEntityById(Long id);

}
