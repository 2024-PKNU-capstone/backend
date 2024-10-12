package com.example.jangboo.file.producer;

import com.example.jangboo.global.dto.KafkaPayload;
import com.example.jangboo.global.dto.ocr.OcrReq;
import com.example.jangboo.global.enums.EventCode;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class FileProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public FileProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessageOcrStart(OcrReq.OcrStartReq request){
        Message<KafkaPayload> message = MessageBuilder
                .withPayload(KafkaPayload.of(EventCode.OCR_START, request))
                .setHeader(KafkaHeaders.TOPIC,EventCode.OCR_START.getTopic())
                .build();
        kafkaTemplate.send(message);
    }

}
