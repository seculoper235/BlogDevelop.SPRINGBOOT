package com.example.blogdevelop.Web.Setting.Dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileResponse {
    private String id;
    private String profile;
    private String nickName;
    private String email;
    private String description;

    @Builder
    public ProfileResponse(String id, String profile, String nickName, String email, String description) {
        this.id = id;
        this.profile = profile;
        this.nickName = nickName;
        this.email = email;
        this.description = description;
    }
}
