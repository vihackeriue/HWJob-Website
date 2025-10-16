package com.hw.hwjobbackend.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;

public interface ApiClientService {
    <T> T get(String url, ParameterizedTypeReference<T> responseType);

    <T> T get(String url, HttpHeaders headers, ParameterizedTypeReference<T> responseType);
}
