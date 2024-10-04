package com.example.jangboo.oauth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.example.jangboo.global.dto.ResultDto;
import com.example.jangboo.oauth.service.OAuthBankService;

@RestController
@RequestMapping("/api/oauth")
public class OAuthBankController {
	private final OAuthBankService oAuthBankService;

	@Autowired
	public OAuthBankController(OAuthBankService oAuthBankService) {
		this.oAuthBankService = oAuthBankService;
	}

	@GetMapping("/open-bank")
	public RedirectView redirectToOpenBank() {
		return new RedirectView(oAuthBankService.getAuthUrl());
	}

	@GetMapping("/token")
	public ResponseEntity<ResultDto<Void>> getOpenBankToken(
		@RequestParam String code,
		@RequestParam String scope,
		@RequestParam String state
	) throws Exception {
		return ResponseEntity.ok(ResultDto.of(200,"토큰 발급이 완료되었습니다.", oAuthBankService.getAccessToken(code)));
	}
}
