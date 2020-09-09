package com.cocktailpick.back.cocktails.cocktail.service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cocktailpick.back.cocktails.cocktail.domain.Cocktail;
import com.cocktailpick.back.cocktails.cocktail.domain.CocktailFindStrategyFactory;
import com.cocktailpick.back.cocktails.cocktail.domain.CocktailRepository;
import com.cocktailpick.back.cocktails.cocktail.domain.CocktailSearcher;
import com.cocktailpick.back.cocktails.cocktail.dto.CocktailDetailResponse;
import com.cocktailpick.back.cocktails.cocktail.dto.CocktailRequest;
import com.cocktailpick.back.cocktails.cocktail.dto.CocktailResponse;
import com.cocktailpick.back.cocktails.recipe.domain.RecipeItem;
import com.cocktailpick.back.common.domain.DailyDate;
import com.cocktailpick.back.common.exceptions.EntityNotFoundException;
import com.cocktailpick.back.common.exceptions.ErrorCode;
import com.cocktailpick.back.common.util.NumberOfDaily;
import com.cocktailpick.back.tag.domain.CocktailTag;
import com.cocktailpick.back.tag.domain.Tag;
import com.cocktailpick.back.tag.domain.TagRepository;
import com.cocktailpick.back.users.favorite.domain.Favorites;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@Service
public class CocktailService {
	private final CocktailRepository cocktailRepository;
	private final TagRepository tagRepository;
	private final CocktailFindStrategyFactory cocktailFindStrategyFactory;

	@Transactional(readOnly = true)
	public List<CocktailResponse> findAllCocktails(Favorites favorites) {
		return Collections.unmodifiableList(CocktailResponse.listOf(cocktailRepository.findAll(), favorites));
	}

	@Transactional(readOnly = true)
	public List<CocktailResponse> findPageContainingWord(String contain, long id, int size, Favorites favorites) {
		Pageable pageRequest = PageRequest.of(0, size);

		List<Cocktail> cocktails = cocktailRepository.findByNameContainingAndIdGreaterThan(contain, id, pageRequest)
			.getContent();

		return Collections.unmodifiableList(CocktailResponse.listOf(cocktails, favorites));
	}

	@Transactional(readOnly = true)
	public List<CocktailResponse> findPageFilteredByTags(List<Long> tagIds, long id, int size,
		Favorites favorites) {
		List<Cocktail> persistCocktails = cocktailRepository.findByIdGreaterThan(id);

		List<Cocktail> cocktails = persistCocktails.stream()
			.filter(cocktail -> cocktail.containTagIds(tagIds))
			.limit(size)
			.collect(Collectors.toList());

		return CocktailResponse.listOf(cocktails, favorites);
	}

	@Transactional(readOnly = true)
	public CocktailDetailResponse findCocktail(Long id, Favorites favorites) {
		Cocktail cocktail = findById(id);
		return CocktailDetailResponse.of(cocktail, favorites.isContainCocktail(cocktail));
	}

	@Transactional
	public Long save(CocktailRequest cocktailRequest) {
		Cocktail cocktail = cocktailRequest.toCocktail();
		cocktailRepository.save(cocktail);

		List<RecipeItem> recipeItems = cocktailRequest.toRecipeItems();
		setCocktail(cocktail, recipeItems);

		List<Tag> tags = tagRepository.findByNameIn(cocktailRequest.getTag());
		associate(cocktail, tags);

		return cocktail.getId();
	}

	@Transactional
	public void updateCocktail(Long id, CocktailRequest cocktailRequest) {
		Cocktail cocktail = findById(id);
		Cocktail requestCocktail = cocktailRequest.toCocktail();
		List<RecipeItem> recipeItems = cocktailRequest.toRecipeItems();
		List<Tag> tags = tagRepository.findByNameIn(cocktailRequest.getTag());

		cocktail.update(requestCocktail, tags, recipeItems);
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

	private void setCocktail(Cocktail cocktail, List<RecipeItem> recipeItems) {
		for (RecipeItem recipeItem : recipeItems) {
			recipeItem.setCocktail(cocktail);
		}
	}

	private void associate(Cocktail cocktail, List<Tag> tags) {
		for (Tag tag : tags) {
			CocktailTag.associate(cocktail, tag);
		}
	}

	@Transactional(readOnly = true)
	public CocktailResponse findCocktailOfToday() {
		DailyDate dailyDate = DailyDate.of(new Date());
		CocktailSearcher cocktailSearcher = cocktailFindStrategyFactory.createCocktailSearcher(
			NumberOfDaily.generateBy(dailyDate));

		List<Cocktail> cocktails = cocktailRepository.findAll();

		Cocktail cocktailOfToday = cocktailSearcher.findIn(cocktails);
		return CocktailResponse.of(cocktailOfToday, false);
	}

	@Transactional(readOnly = true)
	public List<CocktailResponse> findByNameContaining(String name) {
		List<Cocktail> cocktailsContainingName = cocktailRepository.findByNameContaining(name);
		return CocktailResponse.listOf(cocktailsContainingName, Favorites.empty());
	}
}
