package com.hw.hwjobbackend.service.shared.level;

import com.hw.hwjobbackend.exception.AppException;
import com.hw.hwjobbackend.exception.ErrorCode;
import com.hw.hwjobbackend.service.mapper.level.LevelMapper;
import com.hw.hwjobbackend.model.dto.response.level.LevelResponse;
import com.hw.hwjobbackend.repository.level.LevelRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class LevelServiceImpl implements LevelService {
    LevelRepository levelRepository;
    LevelMapper levelMapper;

    @Override
    public Page<LevelResponse> getLevels(int page, int size) {
        page = Math.max(page, 0);
        size = size <= 0 ? 10 : size;
        Pageable pageable = PageRequest.of(page, size);
        return levelRepository.findAll(pageable).map(levelMapper::toLevelResponse);
    }

    @Override
    public List<LevelResponse> getAllLevels() {
        return levelRepository.findAll().stream().map(levelMapper::toLevelResponse).toList();
    }


    @Override
    public LevelResponse getLevelById(Long id) {
        return levelRepository.findById(id).map(
                levelMapper::toLevelResponse).orElseThrow(
                () -> new AppException(ErrorCode.LEVEL_NOT_EXISTED));
    }
}
