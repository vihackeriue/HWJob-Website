package com.hw.hwjobbackend.service.shared.job_type;

import com.hw.hwjobbackend.exception.AppException;
import com.hw.hwjobbackend.exception.ErrorCode;
import com.hw.hwjobbackend.service.mapper.job_type.JobTypeMapper;
import com.hw.hwjobbackend.model.dto.response.job_type.JobTypeResponse;
import com.hw.hwjobbackend.model.entity.job_type.JobType;
import com.hw.hwjobbackend.repository.job_type.JobTypeRepository;
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
public class JobTypeServiceImpl implements JobTypeService {

    JobTypeRepository jobTypeRepository;
    JobTypeMapper jobTypeMapper;

    @Override
    @Transactional(readOnly = true)
    public JobTypeResponse getJobTypeById(Long id) {
        JobType jobType = jobTypeRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.JOB_TYPE_NOT_EXISTED));

        return jobTypeMapper.toJobTypeResponse(jobType);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<JobTypeResponse> getJobTypes(int page, int size) {
        Pageable pageable = PaginationUtils.buildPageable(page, size);

        return jobTypeRepository.findAll(pageable)
                .map(jobTypeMapper::toJobTypeResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public List<JobTypeResponse> getAllJobTypes() {
        return jobTypeRepository.findAll().stream()
                .map(jobTypeMapper::toJobTypeResponse)
                .toList();
    }
}