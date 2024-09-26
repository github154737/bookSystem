package com.example.demo;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class JWTtest {
    @Test
    public void testGEN(){
        Map<String,Object> claims = new HashMap<>();
        claims.put("id",1);
        claims.put("username","admin");
        String token = JWT.create()
                .withClaim("user", claims)
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 12))
                .sign(Algorithm.HMAC256("sha256"));
        System.out.println(token);
    }

    @Test
    public void testParse(){
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9" +
                ".eyJ1c2VyIjp7ImlkIjoxLCJ1c2VybmFtZSI6ImFkbWluIn0sImV4cCI6MTcyNjYxOTgyMX0" +
                ".9MJHeBNd7B8Q_X8EGb9tjQucGfNaMMQkfejNl-bG0ls";
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("sha256")).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        System.out.println(decodedJWT.getClaims());
    }
}
