package com.cocktailpick.back.cocktails2.cocktail.infra;

import static com.cocktailpick.back.cocktails2.cocktail.domain.QCocktail.*;
import static com.cocktailpick.back.tag2.domain.QTag.*;
import static com.cocktailpick.back.users2.favorite.domain.QFavorite.*;

import org.springframework.stereotype.Repository;

import com.cocktailpick.back.cocktails2.cocktail.dto.CocktailDetailResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class CocktailDao {
	private final JPAQueryFactory jpaQueryFactory;

	public CocktailDetailResponse findCocktail(Long id, Long userId) {
		return null;
	}
}
