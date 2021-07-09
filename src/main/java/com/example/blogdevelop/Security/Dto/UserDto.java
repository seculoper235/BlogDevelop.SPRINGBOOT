package com.example.blogdevelop.Security.Dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

@Getter
public class UserDto {
    private final String username;
    private final String email;
    private final String image;

    @Builder
    public UserDto(String username, String email, String image) {
        this.username = username;
        this.email = email;
        this.image = image;
    }
}
