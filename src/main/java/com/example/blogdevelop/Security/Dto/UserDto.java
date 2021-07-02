package com.example.blogdevelop.Security.Dto;

import lombok.Getter;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

@Getter
public class UserDto {
    private final String username;
    private final String email;
    private final String image;
    private final Map<String, Object> attributes;
    private final String nameAttributeKey;

    public UserDto(OAuth2User oAuthUser, String nameAttribute) {
        this.username = oAuthUser.getAttribute("name");
        this.email = oAuthUser.getAttribute("email");
        this.image = oAuthUser.getAttribute("profile");
        this.attributes = oAuthUser.getAttributes();
        this.nameAttributeKey = nameAttribute;
    }
}
