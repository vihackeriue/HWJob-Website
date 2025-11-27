package com.hw.hwjobbackend.service.shared.job_type;


import com.hw.hwjobbackend.exception.AppException;
import com.hw.hwjobbackend.exception.ErrorCode;
import com.hw.hwjobbackend.service.mapper.job_type.JobTypeMapper;
import com.hw.hwjobbackend.model.dto.response.job_type.JobTypeResponse;
import com.hw.hwjobbackend.repository.job_type.JobTypeRepository;
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
public class JobTypeServiceImpl implements JobTypeService {

    JobTypeRepository jobTypeRepository;
    JobTypeMapper jobTypeMapper;


    @Override
    public JobTypeResponse getJobTypeById(Long id) {
        return jobTypeRepository.findById(id)
                .map(jobTypeMapper::toJobTypeResponse)
                .orElseThrow(() -> new AppException(ErrorCode.JOB_TYPE_NOT_EXISTED));
    }

    @Override
    public Page<JobTypeResponse> getJobTypes(int page, int size) {

        page = Math.max(page, 0);
        size = size <= 0 ? 10 : size;

        Pageable pageable = PageRequest.of(page, size);
        return jobTypeRepository.findAll(pageable).map(jobTypeMapper::toJobTypeResponse);
    }

    @Override
    public List<JobTypeResponse> getAllJobTypes() {
        return jobTypeRepository.findAll().stream().map(jobTypeMapper::toJobTypeResponse).toList();
    }
}
