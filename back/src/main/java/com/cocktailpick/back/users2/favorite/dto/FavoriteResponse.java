package com.cocktailpick.back.users2.favorite.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class FavoriteResponse {
	private Long id;

	private String name;

	private String imageUrl;
}
