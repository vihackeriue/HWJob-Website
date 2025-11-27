package com.hw.hwjobbackend.service.recruiter.recruiter_user;


import com.hw.hwjobbackend.exception.AppException;
import com.hw.hwjobbackend.exception.ErrorCode;
import com.hw.hwjobbackend.repository.user.UserRepository;
import com.hw.hwjobbackend.service.mapper.user.RecruiterMapper;
import com.hw.hwjobbackend.model.dto.request.user.RecruiterUpdateRequest;
import com.hw.hwjobbackend.model.dto.response.user.RecruiterResponse;
import com.hw.hwjobbackend.model.entity.user.Recruiter;
import com.hw.hwjobbackend.repository.user.RecruiterRepository;
import com.hw.hwjobbackend.service.shared.user.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@PreAuthorize("hasRole('CANDIDATE')")
public class RecruiterUserServiceImpl implements RecruiterUserService {
    RecruiterRepository recruiterRepository;
    RecruiterMapper recruiterMapper;
    UserService userService;
    UserRepository userRepository;

    @Override
    @Transactional
    @PreAuthorize("hasRole('RECRUITER') or hasRole('ADMIN')")
    public RecruiterResponse updateRecruiterInfo(RecruiterUpdateRequest request) {

        SecurityContext context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();

        Recruiter recruiter = recruiterRepository.findByUsername(username).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED)
        );

        if (request.getEmail() != null) {
            userService.validateEmail(recruiter.getEmail(), request.getEmail());
        }

        if (request.getRegionId() != null) {
            userService.updateRegion(recruiter, request);
        }

        recruiterMapper.updateRecruiter(recruiter, request);

        Recruiter savedRecruiter = recruiterRepository.save(recruiter);

        return recruiterMapper.toRecruiterResponse(savedRecruiter);
    }

}
