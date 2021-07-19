package com.example.blogdevelop.Web.Setting.Dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProfileResponse {
    private final String id;
    private final String profile;
    private final String nickName;
    private final String email;
    private final String description;

    @Builder
    public ProfileResponse(String id, String profile, String nickName, String email, String description) {
        this.id = id;
        this.profile = profile;
        this.nickName = nickName;
        this.email = email;
        this.description = description;
    }
}
