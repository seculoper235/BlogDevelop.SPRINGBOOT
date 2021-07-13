package com.example.blogdevelop.Repository;

import com.example.blogdevelop.Domain.UserImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserImageRepository extends JpaRepository<UserImage, Integer> {
    UserImage findByUserId(String id);
}
