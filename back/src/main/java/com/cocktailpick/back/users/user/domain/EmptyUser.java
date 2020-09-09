package com.cocktailpick.back.users.user.domain;

import com.cocktailpick.back.users.favorite.domain.Favorites;

public class EmptyUser extends User {
	public EmptyUser() {
		super(null, null, null, null, null, null, null, null, null, Favorites.empty());
	}
}