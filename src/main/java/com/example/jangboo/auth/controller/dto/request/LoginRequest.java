package com.example.jangboo.auth.controller.dto.request;

public record LoginRequest(
	String loginId,
	String password
) {
}
