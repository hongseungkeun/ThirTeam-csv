package com.sparta.csv.domain.user.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.sparta.csv.config.BCrypUtil;
import com.sparta.csv.config.JwtUtil;
import com.sparta.csv.domain.user.dto.request.SigninRequest;
import com.sparta.csv.domain.user.dto.request.SignupRequest;
import com.sparta.csv.domain.user.dto.response.SigninResponse;
import com.sparta.csv.domain.user.entity.User;
import com.sparta.csv.domain.user.enums.UserRole;
import com.sparta.csv.domain.user.repository.UserRepository;

import jakarta.security.auth.message.AuthException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final UserRepository userRepository;
	private final JwtUtil jwtUtil;

	@Transactional
	public void signup(@Valid SignupRequest signupRequest) {
		String encodedPassword = BCrypUtil.encrypt(signupRequest.password());

		UserRole userRole = UserRole.from(signupRequest.userRole());

		User newUser = User.builder()
			.email(signupRequest.email())
			.password(encodedPassword)
			.nickName(signupRequest.nickname())
			.userRole(userRole)
			.build();

		User savedUser = userRepository.save(newUser);
	}

	public SigninResponse signin(@Valid SigninRequest signinRequest) {
		User user = userRepository.findByEmail(signinRequest.email()).orElseThrow(
			() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "가입되지 않은 유저입니다.")
		);

		if (!BCrypUtil.matches(signinRequest.password(), user.getPassword())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 비밀번호입니다.");
		}

		String bearerToken = createBearerToken(user);

		return SigninResponse.from(bearerToken);
	}

	private String createBearerToken(User user) {
		return jwtUtil.createToken(user.getUserId(), user.getEmail(), user.getNickName(), user.getUserRole());
	}
}
