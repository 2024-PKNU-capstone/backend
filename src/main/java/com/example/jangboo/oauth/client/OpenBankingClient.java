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

import com.example.jangboo.oauth.token.OpenBankingToken;

@Component
public class OpenBankingClient {
	private static final String GRANT_TYPE = "authorization_code";

	@Value("${oauth.open-banking.uri.token-uri}")
	private String tokenUrl;

	@Value("${oauth.open-banking.key.client-id}")
	private String clientId;

	@Value("${oauth.open-banking.key.client-secret}")
	private String clientSecret;

	@Value("${oauth.open-banking.uri.redirect-uri}")
	private String redirectUri;

	private final RestTemplate restTemplate;

	@Autowired
	public OpenBankingClient(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public String requestAccessToken(String authorizationCode) {
		String url = tokenUrl;

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("code", authorizationCode);
		body.add("grant_type", GRANT_TYPE);
		body.add("client_id", clientId);
		body.add("client_secret", clientSecret);
		body.add("redirect_uri", redirectUri);

		HttpEntity<?> request = new HttpEntity<>(body, headers);

		OpenBankingToken response = restTemplate.postForObject(url, request, OpenBankingToken.class);
		assert response != null;
		return response.getAccessToken();
	}

	public String getAuthUrl() {
		return "https://testapi.openbanking.or.kr/oauth/2.0/authorize?response_type=code&client_id="+
			clientId+"&redirect_uri="+redirectUri+"&scope=login inquiry transfer&state=12345678901234567890123456789012&auth_type=0";
	}
}

