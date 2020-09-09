package com.cocktailpick.back.users2.favorite.ui;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cocktailpick.back.security.CurrentUser;
import com.cocktailpick.back.users2.favorite.application.FavoriteQueryService;
import com.cocktailpick.back.users2.favorite.dto.FavoriteResponse;
import com.cocktailpick.back.users2.user.domain.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class FavoriteQueryController {
	private final FavoriteQueryService favoriteQueryService;

	@GetMapping("/me/favorite")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<List<FavoriteResponse>> retrieveFavorites(@CurrentUser User user) {
		return ResponseEntity.ok(favoriteQueryService.getFavorites(user.getId()));
	}
}
