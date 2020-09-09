package com.cocktailpick.back.cocktails2.cocktail.domain;

import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Abv {
	private double abv;

	public boolean isBetween(int max, int min) {
		return abv >= min && abv <= max ;
	}
}
