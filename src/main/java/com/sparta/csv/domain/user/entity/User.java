package com.sparta.csv.domain.user.entity;

import java.util.function.Consumer;

import com.sparta.csv.domain.user.enums.UserRole;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Table(name = "users")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;

	@Column(nullable = false, unique = true)
	private String nickName;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String password;

	@Enumerated(EnumType.STRING)
	private UserRole userRole;

	@Builder
	public User(String nickName, String email, String password, UserRole userRole) {
		this.nickName = nickName;
		this.email = email;
		this.password = password;
		this.userRole = userRole;
	}

	public void updateNickName(String newNickName){
		if(!newNickName.isBlank()) this.nickName=newNickName;
	}

	public void updatePassword(String newPassword){
		if(!newPassword.isBlank()) this.password=newPassword;
	}
}
