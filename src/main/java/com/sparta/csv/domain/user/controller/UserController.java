package com.sparta.csv.domain.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.csv.domain.user.dto.response.UserInfoResponse;
import com.sparta.csv.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

	private final UserService userService;

	@GetMapping("/{userId}")
	public ResponseEntity<UserInfoResponse> getUser(@PathVariable Long userId){
		UserInfoResponse userInfoResponse = userService.getUser(userId);
		return new ResponseEntity<>(userInfoResponse, HttpStatus.OK);
	}
}
