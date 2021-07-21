package com.example.blogdevelop.Repository;

import com.example.blogdevelop.Domain.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostImageRepository extends JpaRepository<PostImage, Integer> {
}
