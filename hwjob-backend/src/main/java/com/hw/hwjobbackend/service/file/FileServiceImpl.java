package com.hw.hwjobbackend.service.file;

import com.hw.hwjobbackend.model.dto.file.FileInfo;
import com.hw.hwjobbackend.model.dto.response.file.FileData;
import com.hw.hwjobbackend.model.dto.response.file.FileResponse;
import com.hw.hwjobbackend.model.entity.file.FileMgmt;
import com.hw.hwjobbackend.exception.AppException;
import com.hw.hwjobbackend.exception.ErrorCode;
import com.hw.hwjobbackend.service.mapper.file.FileMgmtMapper;
import com.hw.hwjobbackend.repository.file.FileMgmtRepository;
import com.hw.hwjobbackend.repository.file.FileRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Override
    public FileResponse uploadFile(MultipartFile file) {

        try {
            // Store file
            FileInfo fileInfo = fileRepository.store(file);
            // Create file management info
            FileMgmt fileMgmt = fileMgmtMapper.toFileMgmt(fileInfo);
            String userId = SecurityContextHolder.getContext().getAuthentication().getName();
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
        FileMgmt fileMgmt = fileMgmtRepository.findById(fileName).orElseThrow(
                () -> new AppException(ErrorCode.FILE_NOT_FOUND));
        Resource resource = fileRepository.read(fileMgmt);

        return new FileData(fileMgmt.getContentType(), resource);
    }

    @Override
    public FileResponse setDefaultAvatarForUser(String username) {
        try {
            // Đọc ảnh avatar mặc định
            Resource defaultAvatar = new ClassPathResource("static/images/default-avatar.png");
            if (!defaultAvatar.exists()) {
                throw new AppException(ErrorCode.FILE_NOT_FOUND);
            }

            // Lưu file vào thư mục của user
            var fileInfo = fileRepository.storeDefaultAvatar(username, defaultAvatar);

            // Lưu metadata vào database
            FileMgmt fileMgmt = fileMgmtMapper.toFileMgmt(fileInfo);
            fileMgmt.setOwnerId(username);
            fileMgmtRepository.save(fileMgmt);

            return FileResponse.builder()
                    .originalFileName(defaultAvatar.getFilename())
                    .url(fileInfo.getUrl())
                    .build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteFileByUrl(String url) {
        if (url == null || url.isBlank()) return;
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
