package com.example.blogdevelop.Web.Post.Dto.Mapper;

import com.example.blogdevelop.Domain.Post;
import com.example.blogdevelop.Web.Post.Dto.PostRequest;
import com.example.blogdevelop.Web.Post.Dto.PostResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostMapper {
    @Builder
    public static Post toEntity(PostRequest postRequest) {
        return Post.builder()
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .fileList(postRequest.getFileList())
                .build();
    }

    @Builder
    public static PostResponse toResponse(Post post) {
        return PostResponse.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .catId(post.getCategory().getId())
                .catName(post.getCategory().getName())
                .user(post.getUser())
                .build();
    }
}
