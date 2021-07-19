package com.example.blogdevelop.Security.Config;

import com.example.blogdevelop.Security.Service.JwtService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
@PropertySource({"classpath:application-jwt.properties"})
public class JwtProvider {
    private final String secret;
    private final Key secretKey;
    private final long tokenValidTime = 30 * 60 * 1000L;
    private final JwtService jwtService;

    public JwtProvider(@Value("${jwt.secret}") String secret, JwtService jwtService) {
        this.secret = secret;
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        this.jwtService = jwtService;
    }

    public String createJwt(String username, List<String> authorities) {
        Date date = new Date();

        return Jwts.builder()
                .setSubject(username)
                .claim("auth", authorities)
                .setIssuedAt(date)
                .setExpiration(new Date(date.getTime() + tokenValidTime))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String resolveJwt(HttpServletRequest request) {
        return request.getHeader("X-AUTH-TOKEN");
    }

    public Authentication getAuthentication(String jwt) {
        String username = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(jwt)
                .getBody().getSubject();

        UserDetails user = jwtService.loadUserByUsername(username);

        return new UsernamePasswordAuthenticationToken(user, jwt, user.getAuthorities());
    }

    public boolean validateJwt(String jwt) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();

            return true;
        } catch (ExpiredJwtException e) {
            log.error("유효하지 않은 JWT 입니다.", e);
        } catch (UnsupportedJwtException e) {
            log.error("지원하지 않는 JWT 입니다.", e);
        } catch (MalformedJwtException e) {
            log.error("JWT의 구조가 올바르지 않습니다.", e);
        } catch (SignatureException e) {
            log.error("올바르지 않은 JWT 서명 입니다.", e);
        } catch (IllegalArgumentException e) {
            log.error("잘못된 JWT 입니다.", e);
        }
        return false;
    }
}
