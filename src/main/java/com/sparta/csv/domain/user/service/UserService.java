package com.sparta.csv.domain.user.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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

	public UserInfoResponse getUser(Long userId) {
		User user=userRepository.findById(userId).orElseThrow(
			() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "조회되는 회원 정보가 없습니다. id: "+userId)
		);

		return UserInfoResponse.from(user);
	}

	public void deleteUser(Long userId) {
		User user = userRepository.findById(userId).orElseThrow(
			() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "조회되는 회원 정보가 없습니다. id: "+userId)
		);

		userRepository.delete(user);
	}

	public void updateUserById(Long userId, UserInfoRequest userInfoRequest) {
		User user = userRepository.findById(userId).orElseThrow(
			() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "조회되는 회원 정보가 없습니다. id: "+userId)
		);

		user.updateNickName(userInfoRequest.nickname());
	}

	@Transactional
	public void updateUserPasswordById(Long userId, UserPasswordRequest userPasswordRequest) {
		User user = userRepository.findById(userId).orElseThrow(
			() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "조회되는 회원 정보가 없습니다. id: "+userId)
		);

		if(!BCrypUtil.matches(userPasswordRequest.password(), user.getPassword())){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 비밀번호입니다.");
		}

		if(BCrypUtil.matches(userPasswordRequest.newPassword(), user.getPassword())){
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "기존과 동일한 비밀번호입니다.");
		}

		user.updatePassword(BCrypUtil.encrypt(userPasswordRequest.newPassword()));
	}

	/* Todo User 조회 메서드 따로 추출하기 */
}
