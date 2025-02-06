package com.sparta.csv.domain.user.service;

import com.sparta.csv.global.config.BCrypUtil;
import com.sparta.csv.global.exception.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.csv.domain.user.dto.request.SigninRequest;
import com.sparta.csv.domain.user.dto.request.UserInfoRequest;
import com.sparta.csv.domain.user.dto.request.UserPasswordRequest;
import com.sparta.csv.domain.user.dto.response.UserInfoResponse;
import com.sparta.csv.domain.user.entity.User;
import com.sparta.csv.domain.user.repository.UserRepository;
import com.sparta.csv.global.exception.error.ErrorCode;

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
		User user = findUserById(userId);

		// 입력한 비밀번호 불일치
		if (!BCrypUtil.matches(userPasswordRequest.password(), user.getPassword())) {
			throw new BadRequestException(ErrorCode.INVALID_PASSWORD);
		}

		// 기존과 동일한 비밀번호로 수정 불가
		if (BCrypUtil.matches(userPasswordRequest.newPassword(), user.getPassword())) {
			throw new BadRequestException(ErrorCode.INVALID_PASSWORD);
		}

		user.updatePassword(BCrypUtil.encrypt(userPasswordRequest.newPassword()));
	}

	/* 기타 메서드 */
	public User findUserById(Long userId) {
		return userRepository.findById(userId).orElseThrow(
			() -> new NotFoundException(ErrorCode.USER_NOT_FOUND)
		);
	}

	public User findUserByEmail(SigninRequest signinRequest) {
		return userRepository.findByEmail(signinRequest.email()).orElseThrow(
			() -> new UnauthorizedException(ErrorCode.UNAUTHORIZED_USER)
		);
	}

	public void checkUserAuthentication(Long userId, Long loginUserId) {
		if (!userId.equals(loginUserId)) {
			throw new ForbiddenException(ErrorCode.FORBIDDEN_USER_ACCESS);
		}
	}
}
