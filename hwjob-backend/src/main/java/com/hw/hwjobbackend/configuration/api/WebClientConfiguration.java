package com.hw.hwjobbackend.configuration.api;


import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;


import java.time.Duration;
import java.util.concurrent.TimeUnit;


/**
 * Cấu hình WebClient cho gọi API .
 */

@Configuration
public class WebClientConfiguration {

    @Bean
    public WebClient.Builder webClientBuilder() {
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000) // Timeout kết nối: 10 giây
                .responseTimeout(Duration.ofSeconds(30))  // Timeout đọc response: 30 giây
                .doOnConnected(connection -> connection
                        .addHandlerLast(new ReadTimeoutHandler(10, TimeUnit.SECONDS)) // Timeout đọc
                        .addHandlerLast(new WriteTimeoutHandler(10, TimeUnit.SECONDS)) // Timeout ghi
                );

        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                // Tăng kích thước bộ đệm cho response lớn (mặc định là 256KB, tăng lên 16MB nếu cần)
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024));
    }


}
