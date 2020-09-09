package com.cocktailpick.back.cocktails2.cocktail.controller;

import java.awt.print.Pageable;
import java.util.List;

import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cocktailpick.back.cocktails2.cocktail.dto.CocktailDetailResponse;
import com.cocktailpick.back.cocktails2.cocktail.dto.CocktailResponse;
import com.cocktailpick.back.cocktails2.cocktail.service.CocktailQueryService;
import com.cocktailpick.back.security.CurrentUser;
import com.cocktailpick.back.users.user.domain.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/cocktails")
@RestController
public class CocktailQueryController {
	private final CocktailQueryService cocktailQueryService;

	@GetMapping("/{id}")
	public ResponseEntity<CocktailDetailResponse> findCocktail(@CurrentUser User user,
		@PathVariable Long id) {
		return ResponseEntity.ok(cocktailQueryService.findCocktail(id, user.getId()));
	}

	@GetMapping
	public ResponseEntity<List<CocktailResponse>> findCocktails(@CurrentUser User user,
		@RequestParam(required = false, defaultValue = "") String contain,
		@RequestParam(required = false, defaultValue = "") List<Long> tagIds,
		Pageable pageable) {
		return ResponseEntity.ok(cocktailQueryService.findCocktails(contain, tagIds, pageable, user.getId()));
	}

	@GetMapping("/today")
	public ResponseEntity<CocktailResponse> findCocktailOfToday() {
		return ResponseEntity.ok(cocktailQueryService.findCocktailOfToday());
	}
}
