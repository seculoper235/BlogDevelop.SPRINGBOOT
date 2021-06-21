package com.example.blogdevelop.Security.Dto;

import com.example.blogdevelop.Domain.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthUserDto {
    private String email;
    private String image;
    private Map<String, Object> attributes;
    private String keyAttributeName;

    @Builder
    public OAuthUserDto(String email, String image, Map<String, Object> attributes, String keyAttributeName) {
        this.email = email;
        this.image = image;
        this.attributes = attributes;
        this.keyAttributeName = keyAttributeName;
    }

    public OAuthUserDto(User user) {
        //
    }

    public OAuthUserDto ofGoogle(User user) {
        return OAuthUserDto.builder()
                //
                .build();
    }
}
