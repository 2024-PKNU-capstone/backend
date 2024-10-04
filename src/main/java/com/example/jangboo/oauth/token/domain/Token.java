package com.example.jangboo.oauth.token.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "open_banking_token")
public class Token {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "token_id")
	private Long id;

	@Getter
	@Column(name = "access_token")
	private String accessToken;

	@Getter
	@Column(name = "refresh_token")
	private String refreshToken;

	@Getter
	@Column(name = "user_seq_no")
	private String userSeqNo;

	@Column(name ="owner_id")
	private Long ownerId;

	@Builder
	public Token(String accessToken, String refreshToken, String userSeqNo, Long ownerId) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.userSeqNo = userSeqNo;
		this.ownerId = ownerId;
	}

	public void refresh(String accessToken,String refreshToken){
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}
}
