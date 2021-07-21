package com.example.blogdevelop.Web.Post.Dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CategoryResponse {
    private final int id;
    private final String title;
    private final String content;

    @Builder
    public CategoryResponse(int id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }
}
