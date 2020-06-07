package com.budget.envelope.envelopeapi.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;

public final class AuthHelper {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    private AuthHelper() {}

    public static String getUserIdFromHeader(final String authValue) {

        DecodedJWT decodedJwt = JWT.decode(authValue.replace(BEARER_PREFIX, ""));
        return decodedJwt.getClaims().get("sub").asString().split("\\|")[1];
    }
}
