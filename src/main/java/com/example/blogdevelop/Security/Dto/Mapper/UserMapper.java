package com.example.blogdevelop.Security.Dto.Mapper;

import com.example.blogdevelop.Domain.User;
import com.example.blogdevelop.Domain.UserImage;
import com.example.blogdevelop.Security.Dto.UserDto;

public class UserMapper {
    public static User toEntity(UserDto userDto) {
        return User.builder()
                .id("핑핑")
                .password("1234")
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .description(userDto.getImage())
                .build()
                ;
    }
}
