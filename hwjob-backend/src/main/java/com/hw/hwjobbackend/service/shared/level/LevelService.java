package com.hw.hwjobbackend.service.shared.level;

import com.hw.hwjobbackend.model.dto.response.level.LevelResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface LevelService {
    Page<LevelResponse> getLevels(int page, int size);

    List<LevelResponse> getAllLevels();

    LevelResponse getLevelById(Long id);
}
