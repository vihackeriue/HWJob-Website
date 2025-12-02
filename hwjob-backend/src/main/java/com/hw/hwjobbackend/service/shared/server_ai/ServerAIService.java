package com.hw.hwjobbackend.service.shared.server_ai;

import com.hw.hwjobbackend.model.dto.api.ServerAIMessageResponse;

public interface ServerAIService {

    ServerAIMessageResponse sendMessage();

    ServerAIMessageResponse sendMessage(String name);

}
