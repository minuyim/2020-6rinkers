package com.cocktailpick.back.users2.user.domain;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Embeddable
public class AuthInfo {
	@Enumerated(EnumType.STRING)
	private AuthProvider authProvider;

	private String provideId;

	public static AuthInfo local() {
		return new AuthInfo(AuthProvider.LOCAL, null);
	}
}
