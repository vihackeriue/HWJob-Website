package com.hw.hwjobbackend.controller.common;


import com.hw.hwjobbackend.model.dto.api.ServerAIMessageResponse;
import com.hw.hwjobbackend.model.dto.response.ApiResponse;
import com.hw.hwjobbackend.service.shared.server_ai.ServerAIService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/common/server-ais")
public class ServerAIController {

    ServerAIService serverAIService;

    @GetMapping
    public ApiResponse<ServerAIMessageResponse> getMessage() {
        return ApiResponse.<ServerAIMessageResponse>builder()
                .result(serverAIService.sendMessage())
                .build();
    }


    @GetMapping("/hello/{name}")
    public ApiResponse<ServerAIMessageResponse> getMessage(
            @PathVariable String name) {
        return ApiResponse.<ServerAIMessageResponse>builder()
                .result(serverAIService.sendMessage(name))
                .build();
    }

}
