package com.cocktailpick.back.users2.favorite.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
	void deleteByUserIdAndCocktailId(Long userId, Long cocktailId);

	List<Favorite> findByUserId(Long userId);
}
