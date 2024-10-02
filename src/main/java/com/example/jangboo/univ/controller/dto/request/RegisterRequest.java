package com.example.jangboo.univ.controller.dto.request;

public record RegisterRequest(
	String univ,
	String college,
	String dept,
	String name,
	String number,
	String loginId,
	String password) {
}
