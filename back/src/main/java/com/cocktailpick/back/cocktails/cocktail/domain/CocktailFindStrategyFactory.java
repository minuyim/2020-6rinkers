package com.cocktailpick.back.cocktails.cocktail.domain;

import org.springframework.stereotype.Component;

@Component
public class CocktailFindStrategyFactory {
	public CocktailSearcher createCocktailSearcher(long seed) {
		return new CocktailSearcher(seed);
	}
}