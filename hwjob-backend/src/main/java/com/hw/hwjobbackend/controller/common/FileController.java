package com.hw.hwjobbackend.controller.common;


import com.hw.hwjobbackend.service.file.FileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/common/media")
public class FileController {

    FileService fileService;

//    @PutMapping("/upload-avatar")
//    ApiResponse<UpdateAvatarResponse> updateAvatar(
//            @RequestParam("file") MultipartFile file) {
//        return ApiResponse.<UpdateAvatarResponse>builder()
//                .result(userService.updateAvatar(file))
//                .build();
//    }

    @GetMapping("/{fileName}")
    ResponseEntity<Resource> downloadMedia(@PathVariable String fileName) throws IOException {
        var fileData = fileService.downloadFile(fileName);
        return ResponseEntity.<Resource>ok()
                .header(HttpHeaders.CONTENT_TYPE, fileData.contentType())
                .body(fileData.resource());
    }

}
