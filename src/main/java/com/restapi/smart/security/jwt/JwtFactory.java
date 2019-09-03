package com.restapi.smart.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.restapi.smart.security.AccountContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;


@Component
public class JwtFactory {

    private static final Logger log = LoggerFactory.getLogger(JwtFactory.class);

    private static String signingKey = "jwttest";

    public String generateToken(AccountContext context) {

        String token = null;

        //TODO mybatis변경으로 account 메소드도 변경됨
        try {
            token = JWT.create()
                    .withIssuer("kosmo50")
                    .withClaim("USERNAME", context.getAccount().getUserid())
                    .withClaim("USER_ROLE", context.getAccount().getAuth().toString())
                    .sign(generateAlgorithm());

        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return token;
    }

    private Algorithm generateAlgorithm() throws UnsupportedEncodingException {
        return Algorithm.HMAC256(signingKey);
    }

}