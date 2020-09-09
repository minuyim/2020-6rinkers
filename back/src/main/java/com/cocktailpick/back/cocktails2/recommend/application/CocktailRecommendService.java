package com.cocktailpick.back.cocktails2.recommend.application;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cocktailpick.back.cocktails2.cocktail.domain.Cocktail;
import com.cocktailpick.back.cocktails2.cocktail.domain.CocktailRepository;
import com.cocktailpick.back.cocktails2.cocktail.dto.CocktailDetailResponse;
import com.cocktailpick.back.cocktails2.recommend.dto.RecommendRequest;
import com.cocktailpick.back.users.favorite.domain.Favorites;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CocktailRecommendService {
	private final CocktailRepository cocktailRepository;
	private final FilteringAndScoringRecommendService filteringAndScoringRecommendService;

	public List<CocktailDetailResponse> recommend(RecommendRequest recommendRequest, Favorites favorites) {
		List<Cocktail> recommend = filteringAndScoringRecommendService.recommend(cocktailRepository.findAll(), recommendRequest);

		return CocktailDetailResponse.listOf(recommend, favorites);
	}
}
