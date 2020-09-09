package com.cocktailpick.back.cocktails2.cocktail.domain;

import javax.persistence.Embeddable;

import com.cocktailpick.back.tag.domain.Tag;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class CocktailAttribute {
	private Abv abv;

	private Flavor flavor;

	private AttributeDetail attributeDetail;

	public boolean isSweet() {
		return flavor.isSweet();
	}

	public boolean isSour() {
		return flavor.isSour();
	}

	public boolean isBitter() {
		return flavor.isBitter();
	}

	public boolean isAbvBetween(int max, int min) {
		return abv.isBetween(max, min);
	}

	public boolean containsAttribute(Long id) {
		return attributeDetail.contains(id);
	}
}
