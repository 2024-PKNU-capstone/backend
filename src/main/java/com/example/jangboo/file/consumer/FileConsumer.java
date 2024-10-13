package com.example.jangboo.file.consumer;

import com.example.jangboo.file.domain.FileStatus;
import com.example.jangboo.file.service.FileService;
import com.example.jangboo.global.dto.KafkaPayload;
import com.example.jangboo.global.dto.ocr.OcrRes;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileConsumer {

    private final FileService fileService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "ocr_fail", groupId = "group_jangboo", containerFactory = "concurrentListener")
    public void ocrFail(KafkaPayload message) throws Exception {
        OcrRes.OcrFailResponse response = objectMapper.convertValue(message.getData(), OcrRes.OcrFailResponse.class);
        fileService.updateFileStatus(response.getFileId(), FileStatus.FAILED);
    }
}
