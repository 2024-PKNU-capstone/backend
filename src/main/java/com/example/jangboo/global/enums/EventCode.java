package com.example.jangboo.global.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EventCode {
    OCR_START("EVENT-OCR-001", "OCR Start", "ocr_start"),
    OCR_END("EVENT-OCR-002", "OCR End", "ocr_end"),
    OCR_FAIL("EVENT-OCR-003", "OCR Fail", "ocr_fail"),

    RECEIPT_CREATE("EVENT-RECEIPT-001", "Receipt Create", "receipt_create"),
    TRANSACTION_MATCH_SUCCESS("EVENT-TRAN-001", "Transaction Match Success", "transaction_match_success"),
    TRANSACTION_MATCH_FAIL("EVENT-TRAN-002", "Transaction Match Failure", "transaction_match_fail");

    private final String codeName;
    private final String eventName;
    private final String topic;
}