package com.example.jangboo.oauth.token.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "open_banking_token")
public class Token {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "token_id")
	private Long id;

	@Column(name = "access_token")
	private String accessToken;

	@Column(name = "refresh_token")
	private String refreshToken;

	@Column(name = "user_seq_no")
	private String userSeqNo;

	@Builder
	public Token(String accessToken, String refreshToken, String userSeqNo) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.userSeqNo = userSeqNo;
	}
}
