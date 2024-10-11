package com.example.jangboo.global.dto;

import com.example.jangboo.global.enums.EventCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KafkaPayload {
    private String codeName;
    private String eventName;
    private Object data;

    public static KafkaPayload of(EventCode eventCode, Object data) {
        return new KafkaPayload(eventCode.name(), eventCode.name(), data);
    }

}
