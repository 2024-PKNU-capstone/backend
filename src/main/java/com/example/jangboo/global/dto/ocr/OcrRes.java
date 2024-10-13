package com.example.jangboo.global.dto.ocr;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class OcrRes {
    private OcrRes() {
        throw new IllegalStateException("VO Class");
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OcrFailResponse {
        private Long fileId;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OcrEndResponse{
        private Long fileId;
        private OcrResponse ocrResponse;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OcrResponse {}
}
