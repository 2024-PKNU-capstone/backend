package com.example.jangboo.global.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor(staticName = "of")
public class ResultDto<D>{
	private final Integer code;
	private final String message;
	private final D data;
}
