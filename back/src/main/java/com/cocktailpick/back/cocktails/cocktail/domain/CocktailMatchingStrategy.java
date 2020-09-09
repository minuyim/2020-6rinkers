package com.cocktailpick.back.cocktails.cocktail.domain;

@FunctionalInterface
public interface CocktailMatchingStrategy {
	boolean match(Cocktail cocktail);
}
