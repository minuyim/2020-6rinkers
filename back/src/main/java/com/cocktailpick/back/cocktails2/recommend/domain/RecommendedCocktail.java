package com.cocktailpick.back.cocktails2.recommend.domain;

import com.cocktailpick.back.cocktails2.cocktail.domain.Cocktail;
import com.cocktailpick.back.cocktails2.cocktail.domain.temp.CocktailMatchingStrategy;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class RecommendedCocktail {
	private static final int INITIAL_SCORE = 0;

	private Cocktail cocktail;
	private int score;

	public static RecommendedCocktail of(Cocktail cocktail) {
		return new RecommendedCocktail(cocktail, INITIAL_SCORE);
	}

	public RecommendedCocktail addScoreByAnswer(CocktailMatchingStrategy cocktailMatchingStrategy,
		UserPreferenceAnswer userPreferenceAnswer) {
		if (cocktailMatchingStrategy.match(cocktail)) {
			return new RecommendedCocktail(cocktail, userPreferenceAnswer.addScore(score));
		}
		return this;
	}

	public boolean isDislike(CocktailMatchingStrategy cocktailMatchingStrategy,
		UserPreferenceAnswer userPreferenceAnswer) {
		return cocktailMatchingStrategy.match(cocktail) && (userPreferenceAnswer == UserPreferenceAnswer.NO);
	}
}
