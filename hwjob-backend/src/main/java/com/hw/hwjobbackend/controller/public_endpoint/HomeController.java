package com.hw.hwjobbackend.controller.public_endpoint;


import com.hw.hwjobbackend.model.dto.response.ApiResponse;
import com.hw.hwjobbackend.model.dto.response.user.RecruiterHomeResponse;
import com.hw.hwjobbackend.service.shared.user.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/public/home")
public class HomeController {

    UserService userService;

    @GetMapping("/top-recruiters")
    public ApiResponse<List<RecruiterHomeResponse>> getTop10Recruiters() {
        return ApiResponse.<List<RecruiterHomeResponse>>builder()
                .result(userService.getTop10Recruiters())
                .build();
    }
}
