package com.hw.hwjobbackend.service.shared.server_ai;

import com.hw.hwjobbackend.model.dto.api.response.ServerAIMessageResponse;
import com.hw.hwjobbackend.repository.http_client.ServerAIFeignClient;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ServerAIServiceImpl implements ServerAIService {

    ServerAIFeignClient serverAIFeignClient;

    @Override
    public ServerAIMessageResponse sendMessage() {
        return serverAIFeignClient.sendMessage();
    }
}
