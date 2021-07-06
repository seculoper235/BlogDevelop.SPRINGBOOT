package com.example.blogdevelop.Security.Dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * <p>OAuth 인증 후, 추가 사용자 정보를 받기위한 Request 객체</p>
 * 해당 사용자의 기본 정보를 가져오기 위해 Access Token이 필요하다
 */
@Getter
@NoArgsConstructor
public class RegistInfo {
    // 입력받는 필드
    private String accessToken;
    private String nickName;
    private String profile;
    private String description;

    // 따로 설정할 필드
    private String username;
    private String email;
    private String image;

    // 빌더 생성자
    @Builder
    public void setUpInfo(SessionUser.Google googleUser) {
        this.username = googleUser.getName();
        this.email = googleUser.getEmail();
        this.image = googleUser.getImage();
    }
}
