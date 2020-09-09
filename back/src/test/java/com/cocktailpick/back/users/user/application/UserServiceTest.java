package com.cocktailpick.back.users.user.application;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cocktailpick.back.cocktails.cocktail.domain.Cocktail;
import com.cocktailpick.back.cocktails.cocktail.domain.CocktailRepository;
import com.cocktailpick.back.users.favorite.domain.Favorite;
import com.cocktailpick.back.users.favorite.domain.Favorites;
import com.cocktailpick.back.users.favorite.dto.FavoriteRequest;
import com.cocktailpick.back.users.favorite.domain.FavoriteRepository;
import com.cocktailpick.back.users.user.domain.AuthProvider;
import com.cocktailpick.back.users.user.domain.Role;
import com.cocktailpick.back.users.user.domain.User;
import com.cocktailpick.back.users.user.domain.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
	@Mock
	private UserRepository userRepository;

	private UserService userService;

	@Mock
	private FavoriteRepository favoriteRepository;

	@Mock
	private CocktailRepository cocktailRepository;

	private User user;

	private Cocktail cocktail;

	private Cocktail cocktail2;

	private Favorite favorite;

	@BeforeEach
	void setUp() {
		userService = new UserService(userRepository, favoriteRepository, cocktailRepository);

		cocktail = Cocktail.builder()
			.id(1L)
			.name("toney")
			.build();

		cocktail2 = Cocktail.builder()
			.id(2L)
			.name("doo")
			.build();

		Favorites favorites = Favorites.empty();

		favorite = new Favorite(1L, user, cocktail);

		favorites.addFavorite(favorite);

		user = new User();
		user.setId(1L);
		user.setEmail("a@email.com");
		user.setEmailVerified(true);
		user.setImageUrl("image.com");
		user.setName("toney");
		user.setPassword("password");
		user.setProvider(AuthProvider.LOCAL);
		user.setRole(Role.ROLE_USER);
		user.setProviderId("local");
		user.setFavorites(favorites);
	}

	@DisplayName("즐겨찾기를 조회한다.")
	@Test
	void findFavoritesTest() {
		assertThat(userService.findFavorites(user).size()).isEqualTo(1);
	}

	@DisplayName("즐겨찾기를 추가한다.")
	@Test
	void addFavoriteTest() {
		when(cocktailRepository.findById(anyLong())).thenReturn(Optional.of(cocktail2));
		when(favoriteRepository.save(any())).thenReturn(favorite);
		userService.addFavorite(user, new FavoriteRequest(2L));

		verify(favoriteRepository).save(any());
	}

	@DisplayName("즐겨찾기를 삭제한다.")
	@Test
	void deleteFavoriteTest() {
		userService.deleteFavorite(user, 1L);

		assertThat(user.getFavorites().getFavorites().size()).isEqualTo(0);
	}
}
