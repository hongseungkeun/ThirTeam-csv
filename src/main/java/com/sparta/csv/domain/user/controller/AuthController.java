package com.sparta.csv.domain.user.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.csv.domain.user.dto.request.SigninRequest;
import com.sparta.csv.domain.user.dto.request.SignupRequest;
import com.sparta.csv.domain.user.dto.response.SigninResponse;
import com.sparta.csv.domain.user.service.AuthService;
import com.sparta.csv.global.util.UriUtil;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthService authService;

	@PostMapping("/signup")
	public ResponseEntity<Void> signup(@Valid @RequestBody SignupRequest signupRequest) {
		authService.signup(signupRequest);

		final URI uri = UriUtil.createUri("/api/auth/login");

		return ResponseEntity.created(uri).build();
	}

	@PostMapping("/signin")
	public ResponseEntity<SigninResponse> signin(@Valid @RequestBody SigninRequest signinRequest) {
		return ResponseEntity.ok(authService.signin(signinRequest));
	}
}
