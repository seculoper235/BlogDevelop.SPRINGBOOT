package com.example.blogdevelop.Security.Dto.Mapper;

import com.example.blogdevelop.Domain.User;
import com.example.blogdevelop.Domain.UserAuthority;
import com.example.blogdevelop.Security.Dto.RegistInfo;

public class InfoMapper {
    public static User toEntity(RegistInfo registInfo) {
        return User.builder()
                .id(registInfo.getNickName())
                .password("1234")
                .username(registInfo.getUsername())
                .email(registInfo.getEmail())
                .description(registInfo.getDescription())
                .userAuthority(UserAuthority.USER)
                .build()
                ;
    }
}
