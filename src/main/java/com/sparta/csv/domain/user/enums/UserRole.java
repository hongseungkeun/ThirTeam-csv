package com.sparta.csv.domain.user.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum UserRole {
	ADMIN("ROLE_ADMIN", "관리자 권한"),
	USER("ROLE_USER", "사용자 권한");

	private final String role;
	private final String description;

	public static UserRole from(String role) {
		return Arrays.stream(UserRole.values())
			.filter(r -> r.name().equalsIgnoreCase(role))
			.findFirst()
			.orElseThrow(RuntimeException::new); /* Todo 상의 후 예외처리 디렉토리 따로 만들기 */
	}
}
