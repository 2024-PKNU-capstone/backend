package com.example.jangboo.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.jangboo.auth.controller.dto.request.RegisterRequest;
import com.example.jangboo.auth.service.AuthService;
import com.example.jangboo.global.dto.ResultDto;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping("/register/{role}")
	public ResponseEntity<ResultDto<Void>> signup(
		@RequestBody RegisterRequest request,
		@PathVariable String role) {
		return ResponseEntity.ok(ResultDto.of(200,"가입이 완료되었습니다", authService.registerUser(request,role)));
	}
}
