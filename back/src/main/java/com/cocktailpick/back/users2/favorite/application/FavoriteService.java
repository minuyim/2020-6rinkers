package com.cocktailpick.back.users2.favorite.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cocktailpick.back.users2.favorite.domain.Favorite;
import com.cocktailpick.back.users2.favorite.domain.FavoriteRepository;
import com.cocktailpick.back.users2.favorite.dto.FavoriteRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FavoriteService {
	private final FavoriteRepository favoriteRepository;

	@Transactional
	public Long addFavorite(Long userId, FavoriteRequest favoriteRequest) {
		Favorite favorite = Favorite.builder()
			.userId(userId)
			.cocktailId(favoriteRequest.getCocktailId())
			.build();
		favorite = favoriteRepository.save(favorite);
		return favorite.getId();
	}

	@Transactional
	public void deleteFavorite(Long userId, Long cocktailId) {
		favoriteRepository.deleteByUserIdAndCocktailId(userId, cocktailId);
	}
}
