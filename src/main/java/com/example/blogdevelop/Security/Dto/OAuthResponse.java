package com.example.blogdevelop.Security.Dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OAuthResponse {
    private String nickName;
    private String oAuthToken;
    private boolean isRegistered;

    @Builder
    public OAuthResponse(String nickName, String oAuthToken, boolean isRegistered) {
        this.nickName = nickName;
        this.oAuthToken = oAuthToken;
        this.isRegistered = isRegistered;
    }
}
