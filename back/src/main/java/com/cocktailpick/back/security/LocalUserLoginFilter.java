package com.cocktailpick.back.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cocktailpick.back.users.user.dto.AuthResponse;
import com.cocktailpick.back.users.user.dto.LoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LocalUserLoginFilter extends UsernamePasswordAuthenticationFilter {
	private final TokenProvider tokenProvider;

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws
		AuthenticationException {
		if (!request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		}
		if (!MediaType.APPLICATION_JSON_VALUE.equals(request.getContentType())) {
			throw new AuthenticationServiceException("JSON ContentType이 아닙니다.");
		}

		LoginRequest loginRequest;
		try {
			loginRequest = new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);
		} catch (IOException e) {
			throw new AuthenticationServiceException("값을 읽는 도중 오류가 발생했습니다.");
		}

		return this.getAuthenticationManager().authenticate(
			new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
		);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
		Authentication authResult) throws IOException {
		String token = tokenProvider.createToken(authResult);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.getWriter().write(new ObjectMapper().writeValueAsString(new AuthResponse(token)));
	}
}
