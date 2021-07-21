package com.example.blogdevelop.Web.Post.Controller;

import com.example.blogdevelop.Web.Post.Service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    // 전체 소제목 리스트 조회

    // 포스트 전체 조회

    // 포스트 한건 조회

    // 포스트 한건 수정

    // 포스트 한건 삭제
}
