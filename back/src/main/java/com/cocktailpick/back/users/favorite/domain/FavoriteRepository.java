package com.cocktailpick.back.users.favorite.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cocktailpick.back.users.favorite.domain.Favorite;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
}
