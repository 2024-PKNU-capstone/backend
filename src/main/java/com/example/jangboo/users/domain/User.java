package com.example.jangboo.users.domain;

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
import lombok.Setter;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="users")
public class User {
	@Getter
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long id;

	@Getter
	@Column(name="dept_id")
	private Long deptId;

	@Column(name="name")
	private String name;

	@Getter
	@Column(name="login_id")
	private String loginId;

	@Getter
	@Column(name="password")
	private String password;

	@Column(name="number")
	private String number;

	@Builder
	public User(Long deptId, String name, String loginId, String password, String number) {
		this.name = name;
		this.deptId = deptId;
		this.loginId = loginId;
		this.password = password;
		this.number = number;
	}
}
