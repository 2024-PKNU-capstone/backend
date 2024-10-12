package com.example.jangboo.global.dto.ocr;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class OcrReq {
    private OcrReq() {
        throw new IllegalStateException("VO class");
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OcrStartReq {
        private Long fileId;
        private String imageUrl;

        public static OcrStartReq of(Long fileId, String imageUrl) {
            return OcrStartReq.builder()
                    .fileId(fileId)
                    .imageUrl(imageUrl)
                    .build();
        }
    }
}
