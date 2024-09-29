package com.example.jangboo.univ.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.jangboo.auth.controller.dto.Info.CurrentUserInfo;
import com.example.jangboo.global.dto.ResultDto;
import com.example.jangboo.univ.service.UnivService;

@RestController
@RequestMapping("/api/univ")
public class UnivController {
	private final UnivService univService;

	public UnivController(UnivService univService) {
		this.univService = univService;
	}

	@GetMapping("/dept/signup-link")
	public ResponseEntity<ResultDto<String>> getUniv(
		@AuthenticationPrincipal CurrentUserInfo userInfo
	){
		return ResponseEntity.ok(ResultDto.of(
			200,"가입링크가 성공적으로 반환되었습니다.", univService.getDeptSignUpLink(userInfo.deptId())));
	}
}
