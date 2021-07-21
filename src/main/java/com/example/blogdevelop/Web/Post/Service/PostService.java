package com.example.blogdevelop.Web.Post.Service;

import com.example.blogdevelop.Repository.PostImageRepository;
import com.example.blogdevelop.Repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostImageRepository postImageRepository;

    // 전체 소제목 리스트 조회

    // 포스트 한건 조회
    // TODO 이미지도 같이 불러와야 함

    // 포스트 한건 수정

    // 포스트 한건 삭제
}
