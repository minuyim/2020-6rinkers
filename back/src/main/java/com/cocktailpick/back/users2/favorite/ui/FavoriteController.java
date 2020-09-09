package com.cocktailpick.back.users2.favorite.ui;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cocktailpick.back.security.CurrentUser;
import com.cocktailpick.back.users2.favorite.application.FavoriteService;
import com.cocktailpick.back.users2.favorite.dto.FavoriteRequest;
import com.cocktailpick.back.users2.user.domain.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class FavoriteController {
	private final FavoriteService favoriteService;

	@PostMapping("/me/favorites")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Void> addFavorite(@CurrentUser User user,
		@RequestBody @Valid FavoriteRequest favoriteRequest) {
		Long favoriteId = favoriteService.addFavorite(user.getId(), favoriteRequest);
		return ResponseEntity.created(URI.create("/api/user/me/favorites/" + favoriteId))
			.build();
	}

	@DeleteMapping("/me/favorites/{cocktailId}")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<Void> deleteFavorite(@CurrentUser User user, @PathVariable Long cocktailId) {
		favoriteService.deleteFavorite(user.getId(), cocktailId);

		return ResponseEntity.noContent().build();
	}
}
