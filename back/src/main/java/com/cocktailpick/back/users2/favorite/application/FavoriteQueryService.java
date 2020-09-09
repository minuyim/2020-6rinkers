package com.cocktailpick.back.users2.favorite.application;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cocktailpick.back.users2.favorite.dto.FavoriteResponse;
import com.cocktailpick.back.users2.favorite.infra.FavoriteDao;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FavoriteQueryService {
	private final FavoriteDao favoriteDao;

	public List<FavoriteResponse> getFavorites(Long userId) {
		return favoriteDao.findFavoriteCocktailsByUserId(userId);
	}
}
