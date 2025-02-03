package com.sparta.csv.domain.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.csv.config.BCrypUtil;
import com.sparta.csv.config.JwtUtil;
import com.sparta.csv.domain.user.dto.request.SignupRequest;
import com.sparta.csv.domain.user.entity.User;
import com.sparta.csv.domain.user.enums.UserRole;
import com.sparta.csv.domain.user.repository.UserRepository;

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
}
