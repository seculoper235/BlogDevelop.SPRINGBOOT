package com.example.blogdevelop.Web.Post.Dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CategoryResponse {
    private final int id;
    private final String name;

    @Builder
    public CategoryResponse(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
