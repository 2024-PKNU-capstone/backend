package com.example.jangboo.oauth.client;

import java.io.IOException;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.http.HttpStatus;

import com.example.jangboo.oauth.token.dto.TokenInfo;
import com.example.jangboo.oauth.token.service.TokenService;

public class OAuthInterceptor implements ClientHttpRequestInterceptor {
	private final OpenBankingClient openBankingClient;
	private final TokenService tokenService;
	private final Long userId;

	public OAuthInterceptor(OpenBankingClient openBankingClient, TokenService tokenService, Long userId) {
		this.openBankingClient = openBankingClient;
		this.tokenService = tokenService;
		this.userId = userId;
	}

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
		TokenInfo token = tokenService.getTokenInfoByUserId(userId);
		String accessToken = token.accessToken();
		String refreshToken = token.refreshToken();

		request.getHeaders().setBearerAuth(accessToken);

		ClientHttpResponse response;
		try {
			response = execution.execute(request, body);
		} catch (HttpClientErrorException e) {
			if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
				TokenInfo newTokenInfo = openBankingClient.refreshAccessToken(refreshToken);

				tokenService.refreshTokens(userId, newTokenInfo);

				request.getHeaders().setBearerAuth(newTokenInfo.accessToken());
				response = execution.execute(request, body);
			} else {
				throw e;
			}
		}

		return response;
	}
}
