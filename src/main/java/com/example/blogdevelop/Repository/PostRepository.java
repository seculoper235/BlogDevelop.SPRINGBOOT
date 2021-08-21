package com.example.blogdevelop.Repository;

import com.example.blogdevelop.Domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findAllByCategory_Id(int cat);
}
