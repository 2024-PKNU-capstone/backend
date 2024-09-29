package com.example.jangboo.users.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.jangboo.univ.controller.dto.request.RegisterRequest;
import com.example.jangboo.users.domain.User;
import com.example.jangboo.users.domain.UserRepository;

@Service
public class UserService {
	@Autowired
	private final UserRepository userRepository;
	@Autowired
	private final PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Transactional
	public Long registerUser(RegisterRequest request,Long orgId) {
		return userRepository.save(
			User.builder()
				.name(request.name())
				.number(request.number())
				.deptId(orgId)
				.loginId(request.loginId())
				.password(encodePassword(request.password()))
				.build()
		).getId();
	}

	private String encodePassword(String password) {
		return passwordEncoder.encode(password);
	}
}
