package com.sparta.csv.domain.common.entity;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.sparta.csv.domain.user.enums.UserRole;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthUser implements UserDetails {

	private final Long UserId;
	private final String email;
	private final String nickname;
	private final UserRole userRole;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singleton(userRole::getRole);
	}

	@Override
	public String getPassword() {
		return "";
	}

	@Override
	public String getUsername() {
		return "";
	}
}
