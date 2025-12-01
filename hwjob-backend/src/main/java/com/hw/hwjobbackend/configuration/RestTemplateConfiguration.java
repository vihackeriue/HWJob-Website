package com.hw.hwjobbackend.configuration;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactoryBuilder;
import org.apache.hc.core5.ssl.SSLContexts;
import org.apache.hc.client5.http.ssl.TrustAllStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

/**
 * Sử dụng để get dữ liệu từ API
 */

@Configuration
public class RestTemplateConfiguration {

    @Value("${app.http.insecure-ssl:true}")
    private boolean insecureSsl;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        if (insecureSsl) {
            try {
                var sslContext = SSLContexts.custom()
                        .loadTrustMaterial(null, TrustAllStrategy.INSTANCE)
                        .build();

                var socketFactory = SSLConnectionSocketFactoryBuilder.create()
                        .setSslContext(sslContext)
                        .setHostnameVerifier(NoopHostnameVerifier.INSTANCE)
                        .build();

                CloseableHttpClient httpClient = HttpClients.custom()
                        .setConnectionManager(
                                PoolingHttpClientConnectionManagerBuilder.create()
                                        .setSSLSocketFactory(socketFactory)
                                        .build()
                        )
                        .evictExpiredConnections()
                        .build();

                HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
                requestFactory.setConnectTimeout(Duration.ofSeconds(10));
                requestFactory.setReadTimeout(Duration.ofSeconds(30));
                return new RestTemplate(requestFactory);
            } catch (Exception e) {
                throw new IllegalStateException("Failed to register insecure RestTemplate", e);
            }
        }

        return builder
                .requestFactory(this::clientHttpRequestFactory)
                .connectTimeout(Duration.ofSeconds(10))
                .readTimeout(Duration.ofSeconds(30))
                .build();
    }

    private ClientHttpRequestFactory clientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(10000); // 10 seconds
        factory.setReadTimeout(30000);    // 30 seconds
        return factory;
    }
}
