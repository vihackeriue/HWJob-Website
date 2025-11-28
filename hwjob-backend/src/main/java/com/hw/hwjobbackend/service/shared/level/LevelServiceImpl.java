package com.hw.hwjobbackend.service.shared.level;

import com.hw.hwjobbackend.exception.AppException;
import com.hw.hwjobbackend.exception.ErrorCode;
import com.hw.hwjobbackend.service.mapper.level.LevelMapper;
import com.hw.hwjobbackend.model.dto.response.level.LevelResponse;
import com.hw.hwjobbackend.model.entity.level.Level;
import com.hw.hwjobbackend.repository.level.LevelRepository;
import com.hw.hwjobbackend.util.PaginationUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class LevelServiceImpl implements LevelService {

    LevelRepository levelRepository;
    LevelMapper levelMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<LevelResponse> getLevels(int page, int size) {
        Pageable pageable = PaginationUtils.buildPageable(page, size);

        return levelRepository.findAll(pageable)
                .map(levelMapper::toLevelResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LevelResponse> getAllLevels() {
        return levelRepository.findAll().stream()
                .map(levelMapper::toLevelResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public LevelResponse getLevelById(Long id) {
        Level level = levelRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.LEVEL_NOT_EXISTED));

        return levelMapper.toLevelResponse(level);
    }
}