package com.example.blogdevelop.Web.Post.Dto.Mapper;

import com.example.blogdevelop.Domain.Post;
import com.example.blogdevelop.Web.Post.Dto.PostRequest;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostMapper {
    @Builder
    public static Post toEntity(PostRequest postRequest) {
        return Post.builder()
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .build();
    }
}
