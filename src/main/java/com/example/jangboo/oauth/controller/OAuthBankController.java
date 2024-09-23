package com.example.jangboo.oauth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.example.jangboo.oauth.OAuthBankService;

@RestController
@RequestMapping("api/oauth")
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
}
