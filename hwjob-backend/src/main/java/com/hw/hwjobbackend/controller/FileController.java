package com.hw.hwjobbackend.controller;


import com.hw.hwjobbackend.model.dto.response.ApiResponse;
import com.hw.hwjobbackend.model.dto.response.user.UpdateAvatarResponse;
import com.hw.hwjobbackend.service.file.FileService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class FileController {

    FileService fileService;


//    @PutMapping("/upload-avatar")
//    ApiResponse<UpdateAvatarResponse> updateAvatar(
//            @RequestParam("file") MultipartFile file) {
//        return ApiResponse.<UpdateAvatarResponse>builder()
//                .result(userService.updateAvatar(file))
//                .build();
//    }


    @GetMapping("/media/{fileName}")
    ResponseEntity<Resource> downloadMedia(@PathVariable String fileName) throws IOException {
        var fileData = fileService.downloadFile(fileName);
        return ResponseEntity.<Resource>ok()
                .header(HttpHeaders.CONTENT_TYPE, fileData.contentType())
                .body(fileData.resource());
    }

}
