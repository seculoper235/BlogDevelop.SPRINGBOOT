package com.example.blogdevelop.Security.Config;

import com.example.blogdevelop.Security.Service.OAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private DataSource dataSource;
    private OAuthService oAuthService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .authoritiesByUsernameQuery("SELECT")
                .usersByUsernameQuery("SELECT")
                .passwordEncoder(new BCryptPasswordEncoder())
                .rolePrefix("ROLE_")
                ;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement()
                .maximumSessions(1)
                .maxSessionsPreventsLogin(true)
            .and()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                ;

        http.oauth2Login()
                .userInfoEndpoint()
                .userService(oAuthService)
            .and()
                .authorizationEndpoint()
                    .baseUri("/login")
                ;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }
}
