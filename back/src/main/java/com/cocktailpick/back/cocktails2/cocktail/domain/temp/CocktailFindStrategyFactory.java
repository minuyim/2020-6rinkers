package com.cocktailpick.back.cocktails2.cocktail.domain.temp;

import org.springframework.stereotype.Component;

@Component
public class CocktailFindStrategyFactory {
	public CocktailSearcher createCocktailSearcher(long seed) {
		return new CocktailSearcher(seed);
	}
}