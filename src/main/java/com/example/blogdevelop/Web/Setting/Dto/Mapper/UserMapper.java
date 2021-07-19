package com.example.blogdevelop.Web.Setting.Dto.Mapper;

import com.example.blogdevelop.Domain.User;
import com.example.blogdevelop.Web.Setting.Dto.ProfileResponse;
import lombok.Getter;

@Getter
public class UserMapper {
    public static ProfileResponse toResponse(User user) {
        return ProfileResponse.builder()
                .id(user.getId())
                .profile(user.getProfile().getSaveName())
                .nickName(user.getUsername())
                .email(user.getEmail())
                .description(user.getDescription())
                .build();
    }
}
