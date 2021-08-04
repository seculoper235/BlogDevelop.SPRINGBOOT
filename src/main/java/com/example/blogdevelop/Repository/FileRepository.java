package com.example.blogdevelop.Repository;

import com.example.blogdevelop.Domain.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<File, Integer> {
    public File findByName(String filename);
    public void deleteAllByPost_Id(int postId);
    public List<File> findAllByPostId(int postId);
}
