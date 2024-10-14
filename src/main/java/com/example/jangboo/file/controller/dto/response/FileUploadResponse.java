package com.example.jangboo.file.controller.dto.response;

import com.example.jangboo.file.domain.FileType;

import java.util.List;

public record FileUploadResponse(
        int successCount,
        int failureCount,
        List<FileUploadResult> fileUploadResults

) {
}
