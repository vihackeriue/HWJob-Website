package com.hw.hwjobbackend.service.admin.job_type;

import com.hw.hwjobbackend.exception.AppException;
import com.hw.hwjobbackend.exception.ErrorCode;
import com.hw.hwjobbackend.service.mapper.job_type.JobTypeMapper;
import com.hw.hwjobbackend.model.dto.request.job_type.JobTypeRequest;
import com.hw.hwjobbackend.model.dto.response.job_type.JobTypeResponse;
import com.hw.hwjobbackend.model.entity.job_type.JobType;
import com.hw.hwjobbackend.repository.job_type.JobTypeRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@PreAuthorize("hasRole('ADMIN')")
public class AdminJobTypeServiceImpl implements AdminJobTypeService {

    JobTypeRepository jobTypeRepository;
    JobTypeMapper jobTypeMapper;

    @Override
    @Transactional
    public JobTypeResponse createJobType(JobTypeRequest request) {
        validateJobTypeNameNotExists(request.getName(), null);
        validateJobTypeCodeNotExists(request.getCode(), null);

        JobType jobType = jobTypeMapper.toJobType(request);
        jobType = jobTypeRepository.save(jobType);

        return jobTypeMapper.toJobTypeResponse(jobType);
    }

    @Override
    @Transactional
    public JobTypeResponse updateJobType(Long id, JobTypeRequest request) {
        JobType jobType = jobTypeRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.JOB_TYPE_NOT_EXISTED));

        validateJobTypeNameNotExists(request.getName(), id);
        validateJobTypeCodeNotExists(request.getCode(), id);

        if (hasNoChanges(jobType, request)) {
            return jobTypeMapper.toJobTypeResponse(jobType);
        }

        jobTypeMapper.updateJobType(request, jobType);

        return jobTypeMapper.toJobTypeResponse(jobType);
    }

    @Override
    @Transactional
    public void deleteJobType(Long id) {
        JobType jobType = jobTypeRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.JOB_TYPE_NOT_EXISTED));

        jobTypeRepository.delete(jobType);
    }

    private void validateJobTypeNameNotExists(String name, Long excludeId) {
        boolean exists = (excludeId == null)
                ? jobTypeRepository.existsByNameIgnoreCase(name)
                : jobTypeRepository.existsByNameIgnoreCaseAndIdNot(name, excludeId);

        if (exists) {
            throw new AppException(ErrorCode.JOB_TYPE_EXISTED);
        }
    }

    private void validateJobTypeCodeNotExists(String code, Long excludeId) {
        boolean exists = (excludeId == null)
                ? jobTypeRepository.existsByCodeIgnoreCase(code)
                : jobTypeRepository.existsByCodeIgnoreCaseAndIdNot(code, excludeId);

        if (exists) {
            throw new AppException(ErrorCode.JOB_TYPE_CODE_EXISTED);
        }
    }

    private boolean hasNoChanges(JobType jobType, JobTypeRequest request) {
        return Objects.equals(jobType.getName(), request.getName())
                && Objects.equals(jobType.getCode(), request.getCode());
    }
}