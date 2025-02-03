package com.sparta.csv.domain.user.dto.response;

import com.sparta.csv.domain.user.entity.User;

public record UserInfoResponse(
	Long id, String email, String nickName
) {
	public static UserInfoResponse from(User user){
		return new UserInfoResponse(user.getUserId(), user.getEmail(), user.getNickName());
	}
}
