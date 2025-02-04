package com.sparta.csv.domain.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record SignupRequest(
	@NotBlank(message = "이메일 입력은 필수입니다.") @Email
	String email,

	@NotBlank(message = "비밀번호 입력은 필수입니다.")
	@Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,20}$",
		message = "비밀번호는 8자 이상 20자 이내, 대소문자, 숫자, 특수문자를 최소 1개씩 포함하여 생성해주세요.")
	String password,

	@NotBlank(message = "권한(USER/ADMIN) 입력은 필수입니다.")
	String userRole,

	@NotBlank(message = "닉네임 입력은 필수입니다.")
	String nickname
) {
}
