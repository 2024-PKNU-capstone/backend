package com.example.jangboo.oauth;

import org.springframework.stereotype.Component;

import com.example.jangboo.oauth.client.OpenBankingClient;

@Component
public class OAuthBankService {
	private final OpenBankingClient client;

	public OAuthBankService(OpenBankingClient client) {
		this.client = client;
	}

	public String getAuthUrl(){
		return client.getAuthUrl();
	}
}
