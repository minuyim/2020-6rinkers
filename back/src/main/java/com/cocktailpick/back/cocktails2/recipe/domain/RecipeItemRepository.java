package com.cocktailpick.back.cocktails2.recipe.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeItemRepository extends JpaRepository<RecipeItem, Long> {
}
