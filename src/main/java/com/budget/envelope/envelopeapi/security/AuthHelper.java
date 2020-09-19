package com.budget.envelope.envelopeapi.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;

public final class AuthHelper {

    private static final String BEARER_PREFIX = "Bearer ";

    private AuthHelper() {}

    public static String getUserIdFromHeader(final String authValue) {

        DecodedJWT decodedJwt = JWT.decode(authValue.replace(BEARER_PREFIX, ""));
        String userId = decodedJwt.getClaims().get("sub").asString().split("\\|")[1];
        System.out.println("user_id: " + userId);
        return userId;
    }
}
