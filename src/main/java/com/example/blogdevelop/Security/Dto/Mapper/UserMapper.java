package com.example.blogdevelop.Security.Dto.Mapper;

import com.example.blogdevelop.Domain.User;
import com.example.blogdevelop.Domain.UserImage;
import com.example.blogdevelop.Security.Dto.UserDto;

public class UserMapper {
    public static User toEntity(UserDto userDto) {
        return User.builder()
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .userImage(UserImage.builder()
                        .build())
                .build()
                ;
    }
}
