package com.hw.hwjobbackend.service.job_type;

import com.hw.hwjobbackend.model.dto.request.job_type.JobTypeRequest;
import com.hw.hwjobbackend.model.dto.response.job_type.JobTypeResponse;
import com.hw.hwjobbackend.model.entity.JobType;
import com.hw.hwjobbackend.exception.AppException;
import com.hw.hwjobbackend.exception.ErrorCode;
import com.hw.hwjobbackend.mapper.job_type.JobTypeMapper;
import com.hw.hwjobbackend.repository.job_type.JobTypeRepository;
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
public class JobTypeServiceImpl implements JobTypeService {

    JobTypeRepository jobTypeRepository;
    JobTypeMapper jobTypeMapper;


    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public JobTypeResponse createJobType(JobTypeRequest request) {
        if (jobTypeRepository.existsByName(request.getName())) {
            throw new AppException(ErrorCode.JOB_TYPE_EXISTED);
        }
        if (jobTypeRepository.existsByCode(request.getCode())) {
            throw new AppException(ErrorCode.JOB_TYPE_CODE_EXISTED);
        }
        JobType jobType = jobTypeMapper.toJobType(request);
        jobType = jobTypeRepository.save(jobType);
        return jobTypeMapper.toJobTypeResponse(jobType);
    }

    @Override
    public JobTypeResponse getJobTypeById(Long id) {
        return jobTypeRepository.findById(id)
                .map(jobTypeMapper::toJobTypeResponse)
                .orElseThrow(() -> new AppException(ErrorCode.JOB_TYPE_NOT_EXISTED));
    }

    @Override
    public Page<JobTypeResponse> getAllJobTypes(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return jobTypeRepository.findAll(pageable).map(jobTypeMapper::toJobTypeResponse);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public JobTypeResponse updateJobType(Long id, JobTypeRequest request) {
        JobType jobType = jobTypeRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.JOB_TYPE_NOT_EXISTED));

        jobType.setName(request.getName());
        jobType.setCode(request.getCode());
        jobType = jobTypeRepository.save(jobType);

        return jobTypeMapper.toJobTypeResponse(jobType);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteJobType(Long id) {
        JobType jobType = jobTypeRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.JOB_TYPE_NOT_EXISTED));
        jobTypeRepository.delete(jobType);
    }

    @Override
    public JobType getJobTypeEntityById(Long id) {
        return jobTypeRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.JOB_TYPE_NOT_EXISTED)
        );
    }
}
