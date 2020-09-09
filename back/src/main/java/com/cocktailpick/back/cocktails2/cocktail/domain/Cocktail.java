package com.cocktailpick.back.cocktails2.cocktail.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import com.cocktailpick.back.cocktails2.recipe.domain.Recipe;
import com.cocktailpick.back.common.domain.BaseTimeEntity;
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
@Entity
public class Cocktail extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cocktail_sequence_gen")
	@SequenceGenerator(name = "cocktail_sequence_gen", sequenceName = "cocktail_sequence")
	private Long id;

	private CocktailInfo cocktailInfo;

	private CocktailAttribute cocktailAttribute;

	private Recipe recipe;

	public void update(Cocktail requestCocktail) {
		this.cocktailInfo = requestCocktail.cocktailInfo;
		this.cocktailAttribute = requestCocktail.cocktailAttribute;
		this.recipe = requestCocktail.recipe;
	}

	public boolean containsAttribute(Long tagId) {
		return cocktailAttribute.containsAttribute(tagId);
	}

	public boolean containAllAttributes(List<Long> tagIds) {
		return tagIds.stream()
			.allMatch(this::containsAttribute);
	}

	public boolean isSweet() {
		return cocktailAttribute.isSweet();
	}

	public boolean isSour() {
		return cocktailAttribute.isSour();
	}

	public boolean isBitter() {
		return cocktailAttribute.isBitter();
	}

	public boolean isAbvBetween(int max, int min) {
		return cocktailAttribute.isAbvBetween(max, min);
	}
}
