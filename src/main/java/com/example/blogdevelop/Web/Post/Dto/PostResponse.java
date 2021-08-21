package com.example.blogdevelop.Web.Post.Dto;

import com.example.blogdevelop.Domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostResponse {
    private final int postId;
    private final String title;
    private final String content;
    private final User user;
    private final int catId;
    private final String catName;

    @Builder
    public PostResponse(int postId, String title, String content, User user, int catId, String catName) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.user = user;
        this.catId = catId;
        this.catName = catName;
    }
}
