package com.example.jangboo.file.controller.dto.response;

import com.example.jangboo.file.domain.FileType;

public record FileUploadResult(
        FileType fileType,
        String fileName,
        boolean success,
        String fileUrl
) {
}
