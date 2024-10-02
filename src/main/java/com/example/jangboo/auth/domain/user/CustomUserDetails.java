package com.example.jangboo.auth.domain.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.jangboo.role.domain.Role;

import java.util.Collection;
import java.util.List;


public class CustomUserDetails implements UserDetails {

	private final Long userId;
	private final Long deptId;
	private final String loginId;
	private final String password;
	private final List<Role> roles;

	public CustomUserDetails(Long userId, Long deptId,String loginId, String password, List<Role> roles) {
		this.userId = userId;
		this.deptId = deptId;
		this.loginId = loginId;
		this.password = password;
		this.roles = roles;
	}

	public Long getDeptId() {
		return deptId;
	}

	public Long getUserId() {
		return userId;
	}


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles.stream()
			.map(role -> new SimpleGrantedAuthority(role.toString()))
			.toList();  // 유저의 권한 목록을 반환
	}


	@Override
	public String getPassword() {
		return password;  // 유저의 비밀번호 반환
	}

	@Override
	public String getUsername() {
		return loginId;  // 유저의 아이디 반환
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;  // 계정이 만료되지 않았는지 여부
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;  // 계정이 잠겨있지 않은지 여부
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;  // 비밀번호가 만료되지 않았는지 여부
	}

	@Override
	public boolean isEnabled() {
		return true;  // 계정이 활성화되었는지 여부
	}
}
