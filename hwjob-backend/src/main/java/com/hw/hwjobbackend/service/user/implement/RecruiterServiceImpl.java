package com.hw.hwjobbackend.service.user.implement;

import com.hw.hwjobbackend.dto.request.user.RecruiterUpdateRequest;
import com.hw.hwjobbackend.dto.response.user.RecruiterResponse;
import com.hw.hwjobbackend.entity.Recruiter;
import com.hw.hwjobbackend.exception.AppException;
import com.hw.hwjobbackend.exception.ErrorCode;
import com.hw.hwjobbackend.mapper.RecruiterMapper;
import com.hw.hwjobbackend.repository.RecruiterRepository;
import com.hw.hwjobbackend.service.user.RecruiterService;
import com.hw.hwjobbackend.service.user.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RecruiterServiceImpl implements RecruiterService {

    RecruiterRepository recruiterRepository;
    RecruiterMapper recruiterMapper;
    UserService userService;

    @Override
    @Transactional
    @PreAuthorize("hasRole('RECRUITER') or hasRole('ADMIN')")
    public RecruiterResponse updateRecruiterInfo(String recruiterId, RecruiterUpdateRequest request) {
        Recruiter recruiter = recruiterRepository.findById(recruiterId)
                .filter(Recruiter.class::isInstance)
                .map(Recruiter.class::cast)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        // Ánh xạ các trường riêng của Recruiter
        recruiterMapper.updateRecruiter(recruiter, request);

        // Sử dụng service chung để cập nhật các trường của User
        userService.updatePassword(recruiter, request.getPassword());
        userService.updateLocation(recruiter, request);

        // Lưu lại và trả về response
        Recruiter savedRecruiter = recruiterRepository.save(recruiter);

        return recruiterMapper.toRecruiterResponse(savedRecruiter);
    }
}
