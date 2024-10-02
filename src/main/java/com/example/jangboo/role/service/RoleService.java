package com.example.jangboo.role.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.jangboo.role.domain.Role;
import com.example.jangboo.role.domain.RoleRepository;
import com.example.jangboo.role.domain.RoleType;

@Service
public class RoleService {
	@Autowired
	private final RoleRepository roleRepository;

	public RoleService(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	@Transactional
	public void createRole(String role, Long userId) {
		roleRepository.save(
			Role.builder()
				.role(RoleType.valueOf(role))
				.studentId(userId)
				.build()
		);
	}
}
