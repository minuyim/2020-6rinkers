package com.cocktailpick.back.cocktails2.cocktail.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Embeddable
public class AttributeDetail {
	private List<Long> tagsId = new ArrayList<>();

	public boolean contains(Long id) {
		return tagsId.contains(id);
	}
}
