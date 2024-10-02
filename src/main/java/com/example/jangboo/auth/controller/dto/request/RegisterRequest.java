package com.example.jangboo.auth.controller.dto.request;

/*
감사 : 학교/단과대/학과
회장 :
총무 :
부회장 :
학생 :
 */
public record RegisterRequest(
	String univ,
	String colleage,
	String dept,
	String name,
	String number,
	String loginId,
	String password) {
}
