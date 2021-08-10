package com.example.blogdevelop.Web.Post.Controller;

import com.example.blogdevelop.Repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryRepository categoryRepository;

    // 모든 카테고리 조회

    // 특정 카테고리의 모든 포스트 조회

    // 카테고리 생성

    // 카테고리 삭제(반드시 빈 카테고리만 삭제 가능)
}
