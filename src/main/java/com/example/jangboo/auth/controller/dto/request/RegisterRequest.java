package com.example.jangboo.auth.controller.dto.request;

public record RegisterRequest(
	String univ,
	String colleage,
	Long deptId,
	String name,
	String number,
	String loginId,
	String password) {
}
