package com.example.blogdevelop.Security.Config;

import com.example.blogdevelop.Security.Service.JwtService;
import io.jsonwebtoken.io.Decoder;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
@PropertySource({"classpath:application-jwt.properties"})
public class JwtProvider {
    private final String secret;
    private final Key secretKey;
    private final long tokenValidTime = 30 * 60 * 1000L;
    private final JwtService jwtService;

    public JwtProvider(@Value("jwt.secret") String secret, JwtService jwtService) {
        this.secret = secret;
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        this.jwtService = jwtService;
    }
}
