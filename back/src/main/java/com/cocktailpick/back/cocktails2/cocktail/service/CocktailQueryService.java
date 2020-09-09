package com.cocktailpick.back.cocktails2.cocktail.service;

import java.awt.print.Pageable;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cocktailpick.back.cocktails2.cocktail.domain.Cocktail;
import com.cocktailpick.back.cocktails2.cocktail.domain.temp.CocktailSearcher;
import com.cocktailpick.back.cocktails2.cocktail.dto.CocktailDetailResponse;
import com.cocktailpick.back.cocktails2.cocktail.dto.CocktailResponse;
import com.cocktailpick.back.cocktails2.cocktail.infra.CocktailDao;
import com.cocktailpick.back.common.domain.DailyDate;
import com.cocktailpick.back.common.util.NumberOfDaily;
import com.cocktailpick.back.users.favorite.domain.Favorites;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CocktailQueryService {
	private final CocktailDao cocktailDao;

	@Transactional(readOnly = true)
	public CocktailDetailResponse findCocktail(Long id, Long userId) {
		return cocktailDao.findCocktail(id, userId);
	}

	// @Transactional(readOnly = true)
	// public List<CocktailResponse> findCocktails(String tagIds, List<Long> id, Pageable pageable,
	// 	Long favorites) {
	// 	List<Cocktail> persistCocktails = cocktailRepository.findByIdGreaterThan(id);
	//
	// 	List<Cocktail> cocktails = persistCocktails.stream()
	// 		.filter(cocktail -> cocktail.containAllAttributes(tagIds))
	// 		.limit(pageable)
	// 		.collect(Collectors.toList());
	//
	// 	return CocktailResponse.listOf(cocktails, favorites);
	// }
	//
	// @Transactional(readOnly = true)
	// public CocktailResponse findCocktailOfToday() {
	// 	DailyDate dailyDate = DailyDate.of(new Date());
	// 	CocktailSearcher cocktailSearcher = cocktailFindStrategyFactory.createCocktailSearcher(
	// 		NumberOfDaily.generateBy(dailyDate));
	//
	// 	List<Cocktail> cocktails = cocktailRepository.findAll();
	//
	// 	Cocktail cocktailOfToday = cocktailSearcher.findIn(cocktails);
	// 	return CocktailResponse.of(cocktailOfToday, false);
	// }
}
