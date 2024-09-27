package com.example.jangboo.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.jangboo.auth.controller.dto.request.RegisterRequest;
import com.example.jangboo.role.service.RoleService;
import com.example.jangboo.users.service.UserService;

@Service
public class AuthService {
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;

	@Transactional
	public Void registerUser(RegisterRequest request, String role) {
		Long userId = userService.registerUser(request);
		roleService.createRole(role, userId);
		return null;
	}

}
