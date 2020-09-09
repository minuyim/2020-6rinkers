package com.cocktailpick.back.cocktails2.cocktail.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cocktailpick.back.cocktails2.cocktail.domain.Cocktail;
import com.cocktailpick.back.cocktails2.cocktail.domain.CocktailRepository;
import com.cocktailpick.back.cocktails2.cocktail.dto.CocktailRequest;
import com.cocktailpick.back.common.exceptions.EntityNotFoundException;
import com.cocktailpick.back.common.exceptions.ErrorCode;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@Service
public class CocktailService {
	private final CocktailRepository cocktailRepository;

	@Transactional
	public Long save(CocktailRequest cocktailRequest) {
		Cocktail cocktail = cocktailRequest.toCocktail();
		cocktailRepository.save(cocktail);

		return cocktail.getId();
	}

	@Transactional
	public void updateCocktail(Long id, CocktailRequest cocktailRequest) {
		Cocktail cocktail = findById(id);
		Cocktail requestCocktail = cocktailRequest.toCocktail();

		cocktail.update(requestCocktail);
	}

	private Cocktail findById(Long id) {
		return cocktailRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.COCKTAIL_NOT_FOUND));
	}

	@Transactional
	public void deleteCocktail(Long id) {
		cocktailRepository.deleteById(id);
	}

	@Transactional
	public void deleteAllCocktails() {
		cocktailRepository.deleteAll();
	}
}
