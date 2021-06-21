package com.example.blogdevelop.Repository;

import com.example.blogdevelop.Domain.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Integer> {
    public File findByName(String name);
}
