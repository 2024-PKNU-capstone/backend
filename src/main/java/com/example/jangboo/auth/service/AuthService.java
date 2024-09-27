package com.example.jangboo.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.jangboo.auth.JwtTokenProvider;
import com.example.jangboo.auth.controller.dto.request.LoginRequest;
import com.example.jangboo.auth.controller.dto.request.RegisterRequest;
import com.example.jangboo.auth.controller.dto.response.JwtToken;
import com.example.jangboo.auth.domain.user.CustomUserDetails;
import com.example.jangboo.auth.domain.user.CustomUserDetailsService;
import com.example.jangboo.role.service.RoleService;
import com.example.jangboo.users.service.UserService;

@Service
public class AuthService {
	@Autowired
	private UserService userService;
	@Autowired
	private CustomUserDetailsService customUserDetailsService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	@Autowired
	private AuthenticationManager authenticationManager;

	@Transactional
	public Void registerUser(RegisterRequest request, String role) {
		Long userId = userService.registerUser(request);
		roleService.createRole(role, userId);
		return null;
	}

	public JwtToken getAuthentication(LoginRequest request) throws Exception {
		try {
			Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.loginId(), request.password())
			);
		} catch (AuthenticationException e) {
			throw new Exception("Incorrect loginId or password", e);
		}

		final CustomUserDetails userDetails = (CustomUserDetails) customUserDetailsService.loadUserByUsername(request.loginId());
		final JwtToken jwt = jwtTokenProvider.generateToken(userDetails);  // 토큰에 userId, deptId 정보 포함
		return jwt;
	}
}
