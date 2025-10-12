package com.hw.hwjobbackend.configuration;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.List;
import java.util.Locale;

/**
 * LocaleResolverConfiguration:
 * - Cấu hình đa ngôn ngữ cho ứng dụng.
 * - Dựa trên header "Accept-Language" của request để chọn ngôn ngữ phù hợp (vi hoặc en).
 * - Khai báo ResourceBundleMessageSource để load các file messages.properties.
 */

@Configuration
@Slf4j
public class LocaleResolverConfiguration extends AcceptHeaderLocaleResolver implements WebMvcConfigurer {

    @Override
    public Locale resolveLocale(HttpServletRequest request) {

        String language = request.getHeader("Accept-Language");
        log.info("Accept-Language: {}", language);
        return StringUtils.hasLength(language) ?
                Locale.lookup(Locale.LanguageRange.parse(language),
                        List.of(new Locale("vi"), new Locale("en"))) :
                Locale.getDefault();
    }

    @Bean
    public ResourceBundleMessageSource bundleMessageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setCacheSeconds(3600);
        return messageSource;
    }
}
