package com.cocktailpick.back.cocktails2.cocktail.domain.temp;

import com.cocktailpick.back.cocktails2.cocktail.domain.Cocktail;

@FunctionalInterface
public interface CocktailMatchingStrategy {
	boolean match(Cocktail cocktail);
}
