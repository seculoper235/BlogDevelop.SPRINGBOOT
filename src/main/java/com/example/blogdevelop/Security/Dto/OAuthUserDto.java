package com.example.blogdevelop.Security.Dto;

import com.example.blogdevelop.Domain.User;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

@Getter
public class OAuthUserDto {
    private final String name;
    private final String email;
    private final String image;
    private final Map<String, Object> attributes;
    private final String keyAttributeName;

    @Builder
    public OAuthUserDto(String name, String email, String image, Map<String, Object> attributes, String keyAttributeName) {
        this.name = name;
        this.email = email;
        this.image = image;
        this.attributes = attributes;
        this.keyAttributeName = keyAttributeName;
    }

    public OAuthUserDto ofSocial(UserDto user) {
        return ofGoogle(user);
    }

    public OAuthUserDto ofGoogle(UserDto user) {
        return OAuthUserDto.builder()
                .name(user.getUsername())
                .email(user.getEmail())
                .image(user.getImage())
                .attributes(user.getAttributes())
                .keyAttributeName(user.getNameAttributeKey())
                .build();
    }
}
