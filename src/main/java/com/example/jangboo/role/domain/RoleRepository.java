package com.example.jangboo.role.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
	List<Role> findByStudentId(Long studentId);
}
