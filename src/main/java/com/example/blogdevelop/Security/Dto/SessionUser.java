package com.example.blogdevelop.Security.Dto;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

public class SessionUser {
    @Getter
    public static class Google implements Serializable {
        private final String name;
        private final String email;
        private final String image;

        /** 구글 전용 객체 **/
        @Builder
        public Google(UserDto userDto) {
            this.name = userDto.getUsername();
            this.email = userDto.getEmail();
            this.image = userDto.getImage();
        }
    }
}
