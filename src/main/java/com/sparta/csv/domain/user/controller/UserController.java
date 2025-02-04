package com.sparta.csv.domain.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.csv.domain.user.dto.request.UserInfoRequest;
import com.sparta.csv.domain.user.dto.request.UserPasswordRequest;
import com.sparta.csv.domain.user.dto.response.UserInfoResponse;
import com.sparta.csv.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

	private final UserService userService;

	@GetMapping("/{userId}")
	public ResponseEntity<UserInfoResponse> getUser(@PathVariable Long userId) {
		return ResponseEntity.ok(userService.getUser(userId));
	}

	@DeleteMapping("/{userId}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
		userService.deleteUser(userId);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping("/{userId}")
	public ResponseEntity<Void> updateUserInfo(
		@PathVariable Long userId,
		@RequestBody UserInfoRequest userInfoRequest
	) {
		userService.updateUserById(userId, userInfoRequest);
		return ResponseEntity.ok().build();
	}

	@PatchMapping("/{userId}/password")
	public ResponseEntity<Void> updateUserPassword(
		@PathVariable Long userId,
		@RequestBody UserPasswordRequest userPasswordRequest
	) {
		userService.updateUserPasswordById(userId, userPasswordRequest);
		return ResponseEntity.ok().build();
	}
}
