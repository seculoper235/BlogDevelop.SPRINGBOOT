package com.example.blogdevelop.Repository;

import com.example.blogdevelop.Domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
}
