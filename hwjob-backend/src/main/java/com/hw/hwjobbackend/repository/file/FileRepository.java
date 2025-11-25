package com.hw.hwjobbackend.repository.file;

import com.hw.hwjobbackend.model.dto.file.FileInfo;
import com.hw.hwjobbackend.model.entity.FileMgmt;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class FileRepository {

    private final FileMgmtRepository fileMgmtRepository;
    @Value("${app.file.storage-dir}")
    String storageDir;

    @Value("${app.file.download-prefix}")
    String urlPrefix;

    public FileInfo store(MultipartFile file) throws IOException {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Path folder = Paths.get(STR."\{storageDir}\{username}/");

        if (!Files.exists(folder)) {
            Files.createDirectories(folder);
        }

        String fileExtension = StringUtils
                .getFilenameExtension(file.getOriginalFilename());
        String fileName = Objects.isNull(fileExtension)
                ? UUID.randomUUID().toString()
                : STR."\{UUID.randomUUID()}.\{fileExtension}";

        Path filePath = folder.resolve(fileName).normalize().toAbsolutePath();

        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        return FileInfo.builder()
                .name(fileName)
                .size(file.getSize())
                .contentType(file.getContentType())
                .md5Checksum(DigestUtils.md5DigestAsHex(file.getInputStream()))
                .path(filePath.toString())
                .url(urlPrefix + fileName)
                .build();
    }

    public Resource read(FileMgmt fileMgmt) throws IOException {
        byte[] data = Files.readAllBytes(Path.of(fileMgmt.getPath()));
        return new ByteArrayResource(data);
    }

    public FileInfo storeDefaultAvatar(String username, Resource defaultResource) throws IOException {

        Path folder = Paths.get(STR."\{storageDir}\{username}/");

        if (!Files.exists(folder)) {
            Files.createDirectories(folder);
        }

        String fileExtension = StringUtils.getFilenameExtension(defaultResource.getFilename());
        String fileName = (fileExtension == null || fileExtension.isEmpty())
                ? UUID.randomUUID().toString()
                : STR."\{UUID.randomUUID()}.\{fileExtension}";

        Path filePath = folder.resolve(fileName).normalize().toAbsolutePath();

        // Copy the resource content to target path
        try (var is = defaultResource.getInputStream()) {
            Files.copy(is, filePath, StandardCopyOption.REPLACE_EXISTING);
        }

        String contentType = Files.probeContentType(filePath);
        if (contentType == null) contentType = "application/octet-stream";

        // Calculate MD5 checksum
        String md5;
        try (var is2 = defaultResource.getInputStream()) {
            md5 = DigestUtils.md5DigestAsHex(is2);
        }

        return FileInfo.builder()
                .name(fileName)
                .size(Files.size(filePath))
                .contentType(contentType)
                .md5Checksum(md5)
                .path(filePath.toString())
                .url(urlPrefix + fileName)
                .build();
    }
}
