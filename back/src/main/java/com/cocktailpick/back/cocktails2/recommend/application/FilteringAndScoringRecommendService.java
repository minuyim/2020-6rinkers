package com.cocktailpick.back.cocktails2.recommend.application;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cocktailpick.back.cocktails2.cocktail.domain.Cocktail;
import com.cocktailpick.back.cocktails2.recommend.domain.RecommendedCocktails;
import com.cocktailpick.back.cocktails2.recommend.dto.FlavorAnswer;
import com.cocktailpick.back.cocktails2.recommend.dto.TagPreferenceAnswer;
import com.cocktailpick.back.cocktails2.recommend.dto.AbvAnswer;
import com.cocktailpick.back.cocktails2.recommend.dto.RecommendRequest;
import com.cocktailpick.back.common.EntityMapper;
import com.cocktailpick.back.tag.domain.Tag;

@Service
public class FilteringAndScoringRecommendService {
	public List<Cocktail> recommend(List<Cocktail> cocktails, RecommendRequest recommendRequest) {
		RecommendedCocktails recommendedCocktails = filterByAbv(RecommendedCocktails.of(cocktails),
			recommendRequest.getAbvAnswer());
		recommendedCocktails = filterByDislike(recommendedCocktails, recommendRequest.getNonPreferenceAnswers());
		recommendedCocktails = addScoreByFlavor(recommendedCocktails, recommendRequest.getFlavorAnswer());
		recommendedCocktails = addScoreByPreference(recommendedCocktails, recommendRequest.getMoodAnswers());
		recommendedCocktails = addScoreByPreference(recommendedCocktails, recommendRequest.getPreferenceAnswers());
		return recommendedCocktails.getSortedCocktailsByScore();
	}

	private RecommendedCocktails filterByAbv(RecommendedCocktails recommendedCocktails, AbvAnswer abvAnswer) {
		return recommendedCocktails.eliminate(
			cocktail -> !cocktail.isAbvBetween(abvAnswer.getMax(), abvAnswer.getMin()));
	}

	private RecommendedCocktails filterByDislike(RecommendedCocktails recommendedCocktails,
		List<TagPreferenceAnswer> nonPreferenceAnswer) {
		for (TagPreferenceAnswer tagPreferenceAnswer : nonPreferenceAnswer) {
			recommendedCocktails = recommendedCocktails.eliminate(
				cocktail -> cocktail.containsAttribute(tagPreferenceAnswer.getTagId()),
				tagPreferenceAnswer.getUserPreferenceAnswer());
		}
		return recommendedCocktails;
	}

	private RecommendedCocktails addScoreByFlavor(RecommendedCocktails recommendedCocktails,
		FlavorAnswer flavorAnswer) {
		recommendedCocktails = recommendedCocktails.addScoreByAnswer(Cocktail::isSweet,
			flavorAnswer.getSweetAnswer())
			.addScoreByAnswer(Cocktail::isBitter, flavorAnswer.getBitterAnswer())
			.addScoreByAnswer(Cocktail::isSour, flavorAnswer.getSourAnswer());
		return recommendedCocktails;
	}

	private RecommendedCocktails addScoreByPreference(RecommendedCocktails recommendedCocktails,
		List<TagPreferenceAnswer> preferenceAnswers) {
		for (TagPreferenceAnswer preferenceAnswer : preferenceAnswers) {
			recommendedCocktails = recommendedCocktails.addScoreByAnswer(
				cocktail -> cocktail.containsAttribute(preferenceAnswer.getTagId()),
				preferenceAnswer.getUserPreferenceAnswer());
		}
		return recommendedCocktails;
	}
}
