package com.hw.hwjobbackend.repository.http_client;


import com.hw.hwjobbackend.model.dto.api.ServerAIMessageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "test-server-ai", url = "${cors.server-api-ai}")
public interface ServerAIFeignClient {

    @GetMapping
    ServerAIMessageResponse sendMessage();


    @GetMapping(value = "/hello/{name}")
    ServerAIMessageResponse sendMessage(@PathVariable String name);

}
