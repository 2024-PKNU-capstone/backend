package com.example.jangboo.oauth.token.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Long> {
	Token findByOwnerId(Long ownerId);
}
