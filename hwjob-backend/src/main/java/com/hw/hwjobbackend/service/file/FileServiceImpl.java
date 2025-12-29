package com.hw.hwjobbackend.service.file;

import com.hw.hwjobbackend.model.dto.file.FileInfo;
import com.hw.hwjobbackend.model.dto.response.file.FileData;
import com.hw.hwjobbackend.model.dto.response.file.FileResponse;
import com.hw.hwjobbackend.model.entity.file.FileMgmt;
import com.hw.hwjobbackend.exception.AppException;
import com.hw.hwjobbackend.exception.ErrorCode;
import com.hw.hwjobbackend.model.entity.user.User;
import com.hw.hwjobbackend.service.mapper.file.FileMgmtMapper;
import com.hw.hwjobbackend.repository.file.FileMgmtRepository;
import com.hw.hwjobbackend.repository.file.FileRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class FileServiceImpl implements FileService {

    FileRepository fileRepository;
    FileMgmtRepository fileMgmtRepository;
    FileMgmtMapper fileMgmtMapper;

    @NonFinal
    @Value("${app.file.default-avatar-resource}")
    String DEFAULT_AVATAR_RESOURCE;

    @NonFinal
    @Value("${app.file.download-prefix}")
    String urlPrefix;

    @Override
    public FileResponse uploadFile(MultipartFile file, String userId) {
        try {
            // Store file
            FileInfo fileInfo = fileRepository.store(file, userId);

            // Create file management info
            FileMgmt fileMgmt = fileMgmtMapper.toFileMgmt(fileInfo);
            fileMgmt.setOwnerId(userId);

            fileMgmtRepository.save(fileMgmt);
            return FileResponse.builder()
                    .originalFileName(file.getOriginalFilename())
                    .url(fileInfo.getUrl())
                    .build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public FileData downloadFile(String fileName) throws IOException {
        // Check if it's the default avatar request (e.g. "default-avatar.png")
        // Assuming the URL structure is .../files/{fileName}
        // If the fileName matches the default avatar name, serve it directly from resources
        if ("default-avatar.png".equals(fileName)) {
            Resource defaultAvatar = new ClassPathResource(DEFAULT_AVATAR_RESOURCE);
            if (defaultAvatar.exists()) {
                return new FileData("image/png", defaultAvatar);
            }
        }

        FileMgmt fileMgmt = fileMgmtRepository.findById(fileName).orElseThrow(
                () -> new AppException(ErrorCode.FILE_NOT_FOUND));
        Resource resource = fileRepository.read(fileMgmt);

        return new FileData(fileMgmt.getContentType(), resource);
    }

    @Override
    public FileResponse setDefaultAvatarForUser(String userId) {
        // Deprecated or modified behavior:
        // Instead of copying file, just return the default URL.
        // But to keep interface contract, we return a FileResponse with the default URL.
        return FileResponse.builder()
                .originalFileName("default-avatar.png")
                .url(getDefaultAvatarUrl())
                .build();
    }

    @Override
    public String getDefaultAvatarUrl() {
        // Return a static URL that points to the default avatar
        // Assuming the download controller handles "default-avatar.png" or similar
        // You might need to adjust this based on how your FileController maps URLs.
        // If urlPrefix is "http://localhost:8080/api/v1/files/", then:
        return urlPrefix + "default-avatar.png";
    }

    @Override
    public void deleteFileByUrl(String url) {
        if (url == null || url.isBlank()) return;

        // Do not delete if it is the default avatar URL
        if (url.equals(getDefaultAvatarUrl())) {
            return;
        }

        fileMgmtRepository.findByUrl(url).ifPresent(fileMgmt -> {
            try {
                if (fileMgmt.getPath() != null) {
                    Files.deleteIfExists(Path.of(fileMgmt.getPath()));
                }
            } catch (IOException e) {
                log.warn("Could not delete file physically at path: {}", fileMgmt.getPath(), e);
            }
            fileMgmtRepository.deleteById(fileMgmt.getId());
        });
    }
}
