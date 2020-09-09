package com.cocktailpick.back.cocktails.cocktail.service;

import static java.util.stream.Collectors.*;

import java.util.List;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.cocktailpick.back.cocktails.cocktail.domain.Cocktail;
import com.cocktailpick.back.cocktails.cocktail.domain.CocktailRepository;
import com.cocktailpick.back.cocktails.cocktail.dto.CocktailDetailResponse;
import com.cocktailpick.back.cocktails.cocktail.dto.RecommendRequest;
import com.cocktailpick.back.common.EntityMapper;
import com.cocktailpick.back.users.favorite.domain.Favorites;
import com.cocktailpick.back.tag.domain.Tag;
import com.cocktailpick.back.tag.domain.TagRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CocktailRecommendService {
	private final CocktailRepository cocktailRepository;
	private final TagRepository tagRepository;
	private final FilteringAndScoringRecommendService filteringAndScoringRecommendService;

	public List<CocktailDetailResponse> recommend(RecommendRequest recommendRequest, Favorites favorites) {
		List<Cocktail> cocktails = cocktailRepository.findAll();

		EntityMapper<Long, Tag> entityMapper = tagRepository.findAll()
			.stream()
			.collect(collectingAndThen(toMap(Tag::getId, Function.identity()), EntityMapper::new));

		List<Cocktail> recommend = filteringAndScoringRecommendService.recommend(cocktails, entityMapper,
			recommendRequest);

		return CocktailDetailResponse.listOf(recommend, favorites);
	}
}
