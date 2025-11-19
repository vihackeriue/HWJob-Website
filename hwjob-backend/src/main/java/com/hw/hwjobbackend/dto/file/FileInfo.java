package com.hw.hwjobbackend.dto.file;

import lombok.*;
import lombok.experimental.FieldDefaults;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FileInfo {
    String id;
    String name;
    String contentType;
    long size;
    String md5Checksum;
    String path;
    String url;
}