package com.example.blogdevelop.Web.Post.Dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostRequest {
    private int postId;
    private String title;
    private String content;

    @Builder
    public PostRequest(int postId, String title, String content) {
        this.postId = postId;
        this.title = title;
        this.content = content;
    }
}
