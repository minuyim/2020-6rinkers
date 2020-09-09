package com.cocktailpick.back.cocktails2.recipe.domain;

import javax.persistence.Embeddable;

import com.cocktailpick.back.common.domain.BaseTimeEntity;

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
public class RecipeItem extends BaseTimeEntity {
	private String ingredient;

	private String quantity;

	public boolean isSameIngredientWith(RecipeItem recipeItem) {
		return this.ingredient.equals(recipeItem.ingredient);
	}
}
