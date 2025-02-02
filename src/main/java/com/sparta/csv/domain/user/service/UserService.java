package com.sparta.csv.domain.user.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
}
