package com.example.jangboo.oauth.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.example.jangboo.oauth.controller.dto.response.OpenBankingTokenResponse;
import com.example.jangboo.oauth.token.dto.TokenInfo;

@Component
public class OpenBankingClient {
	private static final String AUTH_GRANT_TYPE = "authorization_code";
	private static final String REFRESH_GRANT_TYPE = "refresh_token";

	@Value("${oauth.open-banking.uri.token-uri}")
	private String tokenUrl;

	@Value("${oauth.open-banking.key.client-id}")
	private String clientId;

	@Value("${oauth.open-banking.key.client-secret}")
	private String clientSecret;

	@Value("${oauth.open-banking.uri.redirect-uri}")
	private String redirectUri;

	@Value("${oauth.open-banking.scope}")
	private String scope;

	@Value("${oauth.open-banking.uri.user_info-uri}")
	private String userInfoUri;

	private final RestTemplate restTemplate;

	@Autowired
	public OpenBankingClient(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public TokenInfo requestAccessToken(String authorizationCode) {
		String url = tokenUrl;

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("code", authorizationCode);
		body.add("grant_type", AUTH_GRANT_TYPE);
		body.add("client_id", clientId);
		body.add("client_secret", clientSecret);
		body.add("redirect_uri", redirectUri);

		HttpEntity<?> request = new HttpEntity<>(body, headers);

		OpenBankingTokenResponse response = restTemplate.postForObject(url, request, OpenBankingTokenResponse.class);
		assert response != null;
		return new TokenInfo(response.getAccessToken(),response.getRefreshToken(),response.getUserSeqNo());
	}

	public TokenInfo refreshAccessToken(String refreshToken) {
		String url = tokenUrl;

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("client_id", clientId);
		body.add("client_secret", clientSecret);
		body.add("refresh_token", refreshToken);
		body.add("grant_type", REFRESH_GRANT_TYPE);
		body.add("scope", scope);

		HttpEntity<?> request = new HttpEntity<>(body, headers);

		OpenBankingTokenResponse response = restTemplate.postForObject(url, request, OpenBankingTokenResponse.class);
		assert response != null;
		return new TokenInfo(response.getAccessToken(),response.getRefreshToken(),response.getUserSeqNo());
	}

	public String getAuthUrl() {
		return "https://testapi.openbanking.or.kr/oauth/2.0/authorize?response_type=code&client_id="+
			clientId+"&redirect_uri="+redirectUri+"&scope="+scope+"&state=12345678901234567890123456789012&auth_type=0";

	}
}
