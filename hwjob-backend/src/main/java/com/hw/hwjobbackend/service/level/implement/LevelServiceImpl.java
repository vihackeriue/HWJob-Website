package com.hw.hwjobbackend.service.level.implement;

import com.hw.hwjobbackend.dto.request.level.LevelRequest;
import com.hw.hwjobbackend.dto.response.level.LevelResponse;
import com.hw.hwjobbackend.entity.Level;
import com.hw.hwjobbackend.exception.AppException;
import com.hw.hwjobbackend.exception.ErrorCode;
import com.hw.hwjobbackend.mapper.level.LevelMapper;
import com.hw.hwjobbackend.repository.level.LevelRepository;
import com.hw.hwjobbackend.service.level.LevelService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class LevelServiceImpl implements LevelService {

    LevelRepository levelRepository;
    LevelMapper levelMapper;


    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public LevelResponse createLevel(LevelRequest request) {
        if (levelRepository.existsByName((request.getName()))) {
            throw new AppException(ErrorCode.LEVEL_EXISTED);
        }
        Level level = levelMapper.toLevel(request);
        level = levelRepository.save(level);
        return levelMapper.toLevelResponse(level);
    }

    @Override
    public Page<LevelResponse> getAllLevels(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return levelRepository.findAll(pageable).map(levelMapper::toLevelResponse);
    }

    @Override
    public LevelResponse getLevelById(Long id) {
        return levelRepository.findById(id).map(
                levelMapper::toLevelResponse).orElseThrow(
                () -> new AppException(ErrorCode.LEVEL_NOT_EXISTED));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public LevelResponse updateLevel(Long id, LevelRequest request) {
        Level level = levelRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.LEVEL_NOT_EXISTED));
        level.setName(request.getName());
        level = levelRepository.save(level);
        return levelMapper.toLevelResponse(level);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteLevel(Long id) {
        Level level = levelRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.LEVEL_NOT_EXISTED));
        levelRepository.delete(level);
    }

    @Override
    public Level getLevelEntityById(Long id) {
        return levelRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.LEVEL_NOT_EXISTED)
        );
    }

}
