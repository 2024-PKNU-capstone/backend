package com.example.jangboo.users.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="users")
public class User {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long id;

	@Column(name="dept_id")
	private Long deptId;

	@Column(name="name")
	private String name;

	@Column(name="login_id")
	private String loginId;

	@Column(name="password")
	private String password;

	@Column(name="number")
	private String number;
}
