package com.cocktailpick.back.users2.user.dto;

import com.cocktailpick.back.users2.user.domain.User;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class UserResponse {
	private Long id;

	private String name;

	private String email;

	private String imageUrl;

	private String provider;

	private String role;

	public static UserResponse of(User user) {
		return new UserResponse(user.getId(), user.getUserInfo().getName(),
			user.getUserInfo().getEmailInfo().getEmail(), user.getUserInfo().getImageUrl(),
			user.getUserInfo().getAuthInfo().getAuthProvider().name(), user.getUserInfo().getRole().name());
	}
}
