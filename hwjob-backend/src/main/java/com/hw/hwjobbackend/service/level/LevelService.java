package com.hw.hwjobbackend.service.level;

import com.hw.hwjobbackend.model.dto.request.level.LevelRequest;
import com.hw.hwjobbackend.model.dto.response.level.LevelResponse;
import com.hw.hwjobbackend.model.entity.level.Level;
import org.springframework.data.domain.Page;

import java.util.List;

public interface LevelService {
    LevelResponse createLevel(LevelRequest request);

    Page<LevelResponse> getLevels(int page, int size);

    List<LevelResponse> getAllLevels();

    LevelResponse getLevelById(Long id);

    LevelResponse updateLevel(Long id, LevelRequest request);

    void deleteLevel(Long id);

    Level getLevelEntityById(Long id);

}
