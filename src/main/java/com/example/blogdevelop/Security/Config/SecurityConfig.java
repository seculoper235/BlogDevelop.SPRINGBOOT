package com.example.blogdevelop.Security.Config;

import com.example.blogdevelop.Security.Dto.Mapper.TokenResponseClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.session.Session;
import org.springframework.session.data.redis.RedisIndexedSessionRepository;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig<s extends Session> extends WebSecurityConfigurerAdapter {
    private final RedisIndexedSessionRepository sessionRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.sessionManagement()
                .maximumSessions(1)
                .maxSessionsPreventsLogin(true)
                .sessionRegistry(sessionRegistry())
            .and()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                ;

        http.oauth2Login()
                .tokenEndpoint()
                .accessTokenResponseClient(tokenResponseClient())
                ;

        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .anyRequest().authenticated()
                ;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Bean
    SpringSessionBackedSessionRegistry<?> sessionRegistry() {
        return new SpringSessionBackedSessionRegistry<>(sessionRepository);
    }

    @Bean
    TokenResponseClient tokenResponseClient() {
        return new TokenResponseClient();
    }
}
