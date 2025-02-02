package com.sparta.csv.domain.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.csv.domain.user.dto.request.SigninRequest;
import com.sparta.csv.domain.user.dto.request.SignupRequest;
import com.sparta.csv.domain.user.dto.response.SigninResponse;
import com.sparta.csv.domain.user.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthService authService;

	@PostMapping("/signup")
	public ResponseEntity<Void> signup(@Valid @RequestBody SignupRequest signupRequest){
		authService.signup(signupRequest);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@PostMapping("/signin")
	public ResponseEntity<SigninResponse> signin(@Valid @RequestBody SigninRequest signinRequest){
		SigninResponse signinResponse = authService.signin(signinRequest);
		return new ResponseEntity<>(signinResponse, HttpStatus.OK);
	}
}
