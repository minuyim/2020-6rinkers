package com.cocktailpick.back.users.user.acceptance.step;

import static com.cocktailpick.back.common.acceptance.AdminCreate.*;
import static io.restassured.RestAssured.*;

import org.springframework.http.MediaType;

import com.cocktailpick.back.users.user.dto.AuthResponse;
import com.cocktailpick.back.users.user.dto.LoginRequest;
import com.cocktailpick.back.users.user.dto.SignUpRequest;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;

public class AuthAcceptanceStep {

    public static ExtractableResponse<Response> requestSignUp(SignUpRequest signUpRequest) {
        return given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(signUpRequest)
                .when()
                .post("/api/auth/signup")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> requestLogin(LoginRequest loginRequest) {
        return given().log().all()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(loginRequest)
            .when()
            .post("/api/auth/login")
            .then().log().all()
            .extract();
    }

    public static AuthResponse requestTokenByLogin(LoginRequest loginRequest) {
        return requestLogin(loginRequest).as(AuthResponse.class);
    }

    public static AuthResponse requestAdminAuth() {
        LoginRequest loginRequest = new LoginRequest(ADMIN_EMAIL, ADMIN_PASSWORD);

        return requestTokenByLogin(loginRequest);
    }

    public static String toHeaderValue(AuthResponse authResponse) {
        return authResponse.getTokenType() + " " + authResponse.getAccessToken();
    }
}
