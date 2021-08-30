package com.example.blogdevelop.Web.Post.Dto;

import com.example.blogdevelop.Domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostResponse {
    private final int postId;
    private final String title;
    private final String content;
    private final int catId;
    private final String catName;
    private final User user;

    @Builder
    public PostResponse(int postId, String title, String content, int catId, String catName, User user) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.catId = catId;
        this.catName = catName;
        this.user = user;
    }
}
