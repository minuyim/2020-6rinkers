package com.cocktailpick.back.users.user.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cocktailpick.back.cocktails.cocktail.domain.Cocktail;
import com.cocktailpick.back.cocktails.cocktail.domain.CocktailRepository;
import com.cocktailpick.back.cocktails.cocktail.dto.CocktailResponse;
import com.cocktailpick.back.common.exceptions.EntityNotFoundException;
import com.cocktailpick.back.common.exceptions.ErrorCode;
import com.cocktailpick.back.users.favorite.domain.Favorite;
import com.cocktailpick.back.users.favorite.dto.FavoriteRequest;
import com.cocktailpick.back.users.favorite.domain.FavoriteRepository;
import com.cocktailpick.back.users.user.domain.User;
import com.cocktailpick.back.users.user.domain.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@Service
public class UserService {
	private final UserRepository userRepository;
	private final FavoriteRepository favoriteRepository;
	private final CocktailRepository cocktailRepository;

	public List<CocktailResponse> findFavorites(User user) {
		return user.getFavorites().getFavorites().stream()
			.map(Favorite::getCocktail)
			.map(cocktail -> CocktailResponse.of(cocktail, true))
			.collect(Collectors.toList());
	}

	@Transactional
	public Long addFavorite(User user, FavoriteRequest favoriteRequest) {
		Favorite favorite = new Favorite();
		Cocktail cocktail = cocktailRepository.findById(favoriteRequest.getCocktailId())
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.COCKTAIL_NOT_FOUND));

		favorite.setCocktail(cocktail);
		favorite.setUser(user);

		return favoriteRepository.save(favorite).getId();
	}

	@Transactional
	public void deleteFavorite(User user, Long cocktailId) {
		user.deleteFavorite(cocktailId);
		userRepository.save(user);
	}
}
