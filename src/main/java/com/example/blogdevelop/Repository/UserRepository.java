package com.example.blogdevelop.Repository;

import com.example.blogdevelop.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
