package com.cocktailpick.back.users2.favorite.infra;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cocktailpick.back.cocktails.cocktail.domain.QCocktail;
import com.cocktailpick.back.users2.favorite.domain.QFavorite;
import com.cocktailpick.back.users2.favorite.dto.FavoriteResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class FavoriteDao {
	private final JPAQueryFactory jpaQueryFactory;

	public List<FavoriteResponse> findFavoriteCocktailsByUserId(Long userId) {
		QCocktail cocktail = QCocktail.cocktail;
		QFavorite favorite = QFavorite.favorite;

		return jpaQueryFactory.from(cocktail)
			.join(favorite).on(favorite.cocktailId.eq(cocktail.id))
			.select(Projections.constructor(FavoriteResponse.class,
				cocktail.id, cocktail.name, cocktail.imageUrl))
			.where(favorite.userId.eq(userId))
			.fetch();
	}
}
