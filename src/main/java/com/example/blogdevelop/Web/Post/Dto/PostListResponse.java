package com.example.blogdevelop.Web.Post.Dto;

import com.example.blogdevelop.Domain.Category;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostListResponse {
    private final int id;
    private final String title;
    private final int catId;
    private final String catName;
    private final LocalDateTime createdAt;

    @Builder
    public PostListResponse(int id, String title, int catId, String catName, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.catId = catId;
        this.catName = catName;
        this.createdAt = createdAt;
    }
}
