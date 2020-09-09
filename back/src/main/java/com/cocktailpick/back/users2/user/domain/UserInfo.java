package com.cocktailpick.back.users2.user.domain;

import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Embeddable
public class UserInfo {
	private String name;

	private String imageUrl;

	private EmailInfo emailInfo;

	private AuthInfo authInfo;

	private Role role;

	public static UserInfo defaultLocalUser(String name, String email) {
		return new UserInfo(name, null, EmailInfo.from(email), AuthInfo.local(), Role.ROLE_USER);
	}
}
