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
public class EmailInfo {
	private String email;

	private Boolean emailVerified;

	public static EmailInfo from(String email) {
		return new EmailInfo(email, false);
	}
}
