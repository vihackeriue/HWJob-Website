package com.hw.hwjobbackend.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "file_mgmt")
public class FileMgmt {
    @Id
    String id;
    String ownerId;
    String contentType;
    long size;
    String md5Checksum;
    String path;
    String url;
}
