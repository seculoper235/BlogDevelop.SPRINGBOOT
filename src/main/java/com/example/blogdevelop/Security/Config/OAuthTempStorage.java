package com.example.blogdevelop.Security.Config;

import com.example.blogdevelop.Security.Dto.UserDto;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class OAuthTempStorage {
    private static Map<String, UserDto> oauthData;

    @Bean
    public HashMap<String, UserDto> OAuthStorage() {
        return new HashMap<>();
    }
}
