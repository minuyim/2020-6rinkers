package com.cocktailpick.back.cocktails2.cocktail.domain;

import javax.persistence.Embeddable;
import javax.persistence.Lob;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class CocktailInfo {
	private String name;

	@Lob
	private String description;

	@Lob
	private String origin;

	@Lob
	private String imageUrl;
}
