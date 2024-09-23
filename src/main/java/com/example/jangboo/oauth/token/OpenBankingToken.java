package com.example.jangboo.oauth.token;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OpenBankingToken {
	@JsonProperty("access_token")
	private String accessToken;

	@JsonProperty("token_type")
	private String tokenType;

	@JsonProperty("refresh_token")
	private String refreshToken;

	@JsonProperty("expires_in")
	private String expiresIn;

	@JsonProperty("refresh_token_expires_in")
	private String refreshTokenExpiresIn;

	@JsonProperty("scope")
	private String scope;

	@JsonProperty("user_seq_no")
	private String userSeqNo;

	public OpenBankingToken() {
	}

	public String getAccessToken() {
		return accessToken;
	}
}
