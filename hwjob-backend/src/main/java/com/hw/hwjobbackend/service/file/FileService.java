package com.hw.hwjobbackend.service.file;

import com.hw.hwjobbackend.model.dto.response.file.FileData;
import com.hw.hwjobbackend.model.dto.response.file.FileResponse;
import com.hw.hwjobbackend.model.entity.user.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {

    FileResponse uploadFile(MultipartFile file, User user);

    FileData downloadFile(String fileName) throws IOException;

    /**
     * Sao chép avatar mặc định từ resource cho user với username chỉ định
     * và trả về thông tin file (URL) để hiển thị.
     */
    FileResponse setDefaultAvatarForUser(String username);

    /**
     * Xóa file theo URL đã lưu (nếu tồn tại), bao gồm file vật lý và metadata.
     */
    void deleteFileByUrl(String url);
}
