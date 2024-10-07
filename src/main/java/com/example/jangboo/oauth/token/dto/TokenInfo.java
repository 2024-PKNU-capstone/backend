package com.example.jangboo.oauth.token.dto;

public record TokenInfo(
	String accessToken,
	String refreshToken,
	String userSeqNo
) {
}
