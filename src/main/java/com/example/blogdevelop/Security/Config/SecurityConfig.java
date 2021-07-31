package com.example.blogdevelop.Security.Config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtProvider jwtProvider;
    private final CorsFilter corsFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable();
        http.csrf().disable();

        http.addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class);

        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                ;

        http.addFilterBefore(new JwtFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class);

        http.authorizeRequests()
                .antMatchers("/auth/**").permitAll()
                .anyRequest().authenticated()
                ;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }
}
