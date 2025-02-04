package com.sparta.csv.domain.user.service;

import com.sparta.csv.global.config.BCrypUtil;
import com.sparta.csv.global.exception.ForbiddenException;
import com.sparta.csv.global.exception.error.ErrorCode;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.sparta.csv.domain.user.dto.request.SigninRequest;
import com.sparta.csv.domain.user.dto.request.UserInfoRequest;
import com.sparta.csv.domain.user.dto.request.UserPasswordRequest;
import com.sparta.csv.domain.user.dto.response.UserInfoResponse;
import com.sparta.csv.domain.user.entity.User;
import com.sparta.csv.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	@Transactional(readOnly = true)
	public UserInfoResponse getUser(Long userId) {
		User user = findUserById(userId);

		return UserInfoResponse.from(user);
	}

	@Transactional
	public void deleteUser(Long userId) {
		User user = findUserById(userId);

		userRepository.delete(user);
	}

	@Transactional
	public void updateUserById(Long userId, UserInfoRequest userInfoRequest) {
		User user = findUserById(userId);

		user.updateNickName(userInfoRequest.nickname());
	}

	@Transactional
	public void updateUserPasswordById(Long userId, UserPasswordRequest userPasswordRequest) {
		User user = userRepository.findById(userId).orElseThrow(
			() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "조회되는 회원 정보가 없습니다. id: " + userId)
		);

		if (!BCrypUtil.matches(userPasswordRequest.password(), user.getPassword())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 비밀번호입니다.");
		}

		if (BCrypUtil.matches(userPasswordRequest.newPassword(), user.getPassword())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "기존과 동일한 비밀번호입니다.");
		}

		user.updatePassword(BCrypUtil.encrypt(userPasswordRequest.newPassword()));
	}

	/* 기타 메서드 */
	public User findUserById(Long userId) {
		User user = userRepository.findById(userId).orElseThrow(
			() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "조회되는 회원 정보가 없습니다. id: " + userId)
		);
		return user;
	}

	public User findUserByEmail(SigninRequest signinRequest) {
		User user = userRepository.findByEmail(signinRequest.email()).orElseThrow(
			() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "가입되지 않은 유저입니다.")
		);
		return user;
	}

	public void checkUserAuthentication(Long userId, Long loginUserId) {
		if (userId.equals(loginUserId)) {
			throw new ForbiddenException(ErrorCode.FORBIDDEN_USER_ACCESS);
		}
	}
}
