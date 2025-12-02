package com.hw.hwjobbackend.service.api;


import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import reactor.core.publisher.Mono;

/**
 * Service tổng quát để gọi API
 * Có thể tái sử dụng cho nhiều endpoint khác nhau
 */
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApiClientServiceImpl implements ApiClientService {

    WebClient.Builder webClientBuilder;

    // Gọi API GET và trả về response (blocking)

    @Override
    public <T> T get(String url, ParameterizedTypeReference<T> responseType) {
        return get(url, null, responseType);
    }

    // Gọi API GET với headers tùy chỉnh (blocking)

    @Override
    public <T> T get(String url, HttpHeaders headers, ParameterizedTypeReference<T> responseType) {

        try {
            log.debug("Calling API: {}", url);

            WebClient webClient = webClientBuilder.build(); // Tạo WebClient instance

            Mono<T> responseMono = webClient.get()
                    .uri(url)
                    .headers(httpHeaders -> {
                        if (headers != null) {
                            httpHeaders.addAll(headers);
                        }
                    })
                    .retrieve()
                    .onStatus(httpStatusCode -> !httpStatusCode.is2xxSuccessful(), clientResponse -> {
                        log.error("Error calling API: {}", url);
                        return Mono.error(new RuntimeException("API call failed with status: " + clientResponse.statusCode()));
                    })
                    .bodyToMono(responseType);
            T result = responseMono.block();
            log.debug("API call successful: {}", url);
            return result;
        } catch (WebClientException e) {
            log.error("Error calling API: {} - Error: {}", url, e.getMessage(), e);
            throw new RuntimeException("Failed to fetch data from API: " + url, e);
        }
    }
}
