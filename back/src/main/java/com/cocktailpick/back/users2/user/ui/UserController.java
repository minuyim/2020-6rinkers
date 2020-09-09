package com.cocktailpick.back.users2.user.ui;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cocktailpick.back.security.CurrentUser;
import com.cocktailpick.back.users2.user.application.UserService;
import com.cocktailpick.back.users2.user.domain.User;
import com.cocktailpick.back.users2.user.dto.SignUpRequest;
import com.cocktailpick.back.users2.user.dto.UserResponse;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequestMapping("/api/user")
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@RestController
public class UserController {
	private final UserService userService;

	@GetMapping("/me")
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<UserResponse> getCurrentUser(@CurrentUser User user) {
		return ResponseEntity.ok(UserResponse.of(user));
	}

	@PostMapping("/signup")
	public ResponseEntity<Void> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
		Long userId = userService.registerUser(signUpRequest);
		return ResponseEntity.created(URI.create("/api/user/" + userId)).build();
	}
}
