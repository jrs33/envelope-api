package com.budget.envelope.envelopeapi.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;

public final class AuthHelper {

    private static final String BEARER_PREFIX = "Bearer ";

    private AuthHelper() {}

    public static String getUserIdFromHeader(final String authValue) {

        if("true".equals(System.getenv("IS_LOCAL"))) {
            return "local_test_id";
        }

        DecodedJWT decodedJwt = JWT.decode(authValue.replace(BEARER_PREFIX, ""));
        return decodedJwt.getClaims().get("sub").asString().split("\\|")[1];
    }
}
