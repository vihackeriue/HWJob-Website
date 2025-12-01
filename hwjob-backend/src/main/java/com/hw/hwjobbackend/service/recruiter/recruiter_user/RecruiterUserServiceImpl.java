package com.hw.hwjobbackend.service.recruiter.recruiter_user;

import com.hw.hwjobbackend.exception.AppException;
import com.hw.hwjobbackend.exception.ErrorCode;
import com.hw.hwjobbackend.service.mapper.user.RecruiterMapper;
import com.hw.hwjobbackend.model.dto.request.user.RecruiterUpdateRequest;
import com.hw.hwjobbackend.model.dto.response.user.RecruiterResponse;
import com.hw.hwjobbackend.model.entity.user.Recruiter;
import com.hw.hwjobbackend.repository.user.RecruiterRepository;
import com.hw.hwjobbackend.service.shared.user.UserService;
import com.hw.hwjobbackend.util.SecurityUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@PreAuthorize("hasRole('RECRUITER')")
public class RecruiterUserServiceImpl implements RecruiterUserService {
    RecruiterRepository recruiterRepository;
    RecruiterMapper recruiterMapper;
    UserService userService;

    @Override
    public RecruiterResponse updateRecruiterInfo(RecruiterUpdateRequest request) {
        String recruiterId = SecurityUtils.getCurrentUserId();

        Recruiter recruiter = recruiterRepository.findById(recruiterId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        userService.validateAndUpdateEmail(recruiter, request.getEmail());

        userService.validateAndUpdateRegion(recruiter, request.getRegionId());

        recruiterMapper.updateRecruiter(recruiter, request);

        return recruiterMapper.toRecruiterResponse(recruiter);
    }
}