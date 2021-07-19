package com.example.blogdevelop.Security.Config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {
    private final JwtProvider jwtProvider;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // JwtProvider로 JWT 검증 후, Authentication을 생성하여 SecurityContext에 저장
        String jwt = jwtProvider.resolveJwt((HttpServletRequest) servletRequest);

        if(jwt != null && jwtProvider.validateJwt(jwt)) {
            Authentication authentication = jwtProvider.getAuthentication(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        else
            log.error("JWT가 존재하지 않거나, 올바르지 않은 JWT 입니다.");

        filterChain.doFilter(servletRequest, servletResponse);
    }
}
