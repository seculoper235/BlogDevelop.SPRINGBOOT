package com.example.blogdevelop.Web.Setting.Dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProfileRequest {
    private final String nickName;
    private final String description;

    @Builder
    public ProfileRequest(String nickName, String description) {
        this.nickName = nickName;
        this.description = description;
    }
}
