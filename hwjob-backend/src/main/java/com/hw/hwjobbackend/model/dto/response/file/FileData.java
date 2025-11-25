package com.hw.hwjobbackend.model.dto.response.file;

import org.springframework.core.io.Resource;

public record FileData(String contentType, Resource resource) {
}
