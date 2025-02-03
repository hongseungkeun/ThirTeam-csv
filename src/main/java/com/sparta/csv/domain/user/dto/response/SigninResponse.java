package com.sparta.csv.domain.user.dto.response;

public record SigninResponse(String token) {

	public static SigninResponse from(String token){
		return new SigninResponse(token);
	}
}
