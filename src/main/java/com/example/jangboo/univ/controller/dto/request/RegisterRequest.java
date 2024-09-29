package com.example.jangboo.univ.controller.dto.request;

public record RegisterRequest(
	String univ,
	String colleage,
	String dept,
	String name,
	String number,
	String loginId,
	String password) {
}
