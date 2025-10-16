package com.hw.hwjobbackend.service.implement;


import com.hw.hwjobbackend.service.ApiClientService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * Service tổng quát để gọi API external
 * Có thể tái sử dụng cho nhiều endpoint khác nhau
 */
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApiClientServiceImpl implements ApiClientService {


    RestTemplate restTemplate;

    /**
     * Gọi API GET và trả về response
     *
     * @param url          API endpoint
     * @param responseType Class type của response
     * @param <T>          Generic type
     * @return Data từ API
     */
    @Override
    public <T> T get(String url, ParameterizedTypeReference<T> responseType) {
        return get(url, null, responseType);
    }

    /**
     * Gọi API GET với headers tùy chỉnh
     *
     * @param url          API endpoint
     * @param headers      HTTP headers
     * @param responseType Class type của response
     * @param <T>          Generic type
     * @return Data từ API
     */
    @Override
    public <T> T get(String url, HttpHeaders headers, ParameterizedTypeReference<T> responseType) {
        try {
            log.debug("Calling GET API: {}", url);

            HttpEntity<?> entity = headers != null ? new HttpEntity<>(headers) : null;

            ResponseEntity<T> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    responseType
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                log.debug("API call successful: {}", url);
                return response.getBody();
            } else {
                log.warn("API returned non-success status: {} for URL: {}",
                        response.getStatusCode(), url);
                return null;
            }
        } catch (RestClientException e) {
            log.error("Error calling API: {} - Error: {}", url, e.getMessage(), e);
            throw new RuntimeException("Failed to fetch data from API: " + url, e);
        }
    }
}
