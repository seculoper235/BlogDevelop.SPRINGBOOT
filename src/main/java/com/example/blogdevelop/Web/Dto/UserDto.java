package com.example.blogdevelop.Web.Dto;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Size;

@Getter
public class UserDto {
    @NotNull
    @Size(min = 2, max = 7)
    private final String id;

    private final String image;

    private final String description;

    @Builder
    public UserDto(String id, String image, String description) {
        this.id = id;
        this.image = image;
        this.description = description;
    }
}
