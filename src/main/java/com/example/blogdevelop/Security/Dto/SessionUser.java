package com.example.blogdevelop.Security.Dto;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String image;

    /** 구글 전용 객체 **/
    @Getter
    public static class Google {
        private final String name;
        private final String email;
        private final String image;

        @Builder
        public Google(UserDto userDto) {
            this.name = userDto.getUsername();
            this.email = userDto.getEmail();
            this.image = userDto.getImage();
        }
    }
}
