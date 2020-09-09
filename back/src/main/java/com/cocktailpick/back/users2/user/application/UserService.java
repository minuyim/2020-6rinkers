package com.cocktailpick.back.users2.user.application;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cocktailpick.back.common.exceptions.BadRequestException;
import com.cocktailpick.back.users2.user.domain.Password;
import com.cocktailpick.back.users2.user.domain.User;
import com.cocktailpick.back.users2.user.domain.UserInfo;
import com.cocktailpick.back.users2.user.domain.UserRepository;
import com.cocktailpick.back.users2.user.dto.SignUpRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public Long registerUser(SignUpRequest signUpRequest) {
		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			throw new BadRequestException("존재하는 Email입니다.");
		}

		User user = User.builder()
			.password(new Password(passwordEncoder.encode(signUpRequest.getPassword())))
			.userInfo(UserInfo.defaultLocalUser(signUpRequest.getName(), signUpRequest.getEmail()))
			.build();

		User result = userRepository.save(user);
		return result.getId();
	}
}
