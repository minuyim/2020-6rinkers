package com.cocktailpick.back.cocktails2.recipe.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;

import com.cocktailpick.back.common.exceptions.ErrorCode;
import com.cocktailpick.back.common.exceptions.InvalidValueException;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Embeddable
public class Recipe {
	@CollectionTable
	@ElementCollection
	private List<RecipeItem> recipeItems = new ArrayList<>();

	public void removeRecipeItem(RecipeItem recipeItem) {
		recipeItems.remove(recipeItem);
	}

	public void addRecipeItem(RecipeItem recipeItem) {
		if (isContainRecipeItem(recipeItem)) {
			throw new InvalidValueException(ErrorCode.RECIPE_ITEM_DUPLICATED);
		}

		recipeItems.add(recipeItem);
	}

	private boolean isContainRecipeItem(RecipeItem recipeItem) {
		return recipeItems.stream()
			.anyMatch(item -> item.isSameIngredientWith(recipeItem));
	}

	public void clear() {
		recipeItems.clear();
	}
}
