package com.cocktailpick.back.cocktails2.cocktail.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cocktailpick.back.cocktails2.cocktail.service.CocktailService;
import com.cocktailpick.back.cocktails2.cocktail.dto.CocktailDetailResponse;
import com.cocktailpick.back.cocktails2.cocktail.dto.CocktailRequest;
import com.cocktailpick.back.cocktails2.cocktail.dto.CocktailResponse;
import com.cocktailpick.back.cocktails2.recommend.dto.RecommendRequest;
import com.cocktailpick.back.cocktails2.recommend.application.CocktailRecommendService;
import com.cocktailpick.back.security.CurrentUser;
import com.cocktailpick.back.users.user.domain.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/cocktails")
public class CocktailController {
	private final CocktailService cocktailService;
	private final CocktailRecommendService cocktailRecommendService;



	@PostMapping
	public ResponseEntity<Void> addCocktail(@RequestBody @Valid CocktailRequest cocktailRequest) {
		Long saveId = cocktailService.save(cocktailRequest);
		return ResponseEntity.created(URI.create("/api/cocktails/" + saveId)).build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<Void> updateCocktail(@PathVariable Long id,
		@RequestBody @Valid CocktailRequest cocktailRequest) {
		cocktailService.updateCocktail(id, cocktailRequest);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCocktail(@PathVariable Long id) {
		cocktailService.deleteCocktail(id);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping
	public ResponseEntity<Void> deleteAllCocktails() {
		cocktailService.deleteAllCocktails();
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/recommend")
	public ResponseEntity<List<CocktailDetailResponse>> recommend(@CurrentUser User user,
		@RequestBody RecommendRequest recommendRequests) {
		List<CocktailDetailResponse> cocktailDetailResponses = cocktailRecommendService.recommend(recommendRequests,
			user.getFavorites());
		return ResponseEntity.ok(cocktailDetailResponses);
	}
}
