package com.example.blogdevelop.Web.Post.Dto;

import com.example.blogdevelop.Domain.File;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class PostRequest {
    private int postId;
    private String title;
    private String content;
    private List<String> savePathList;
    private List<File> fileList;

    @Builder
    public PostRequest(int postId, String title, String content) {
        this.postId = postId;
        this.title = title;
        this.content = content;
    }

    public void setFileList(List<File> fileList) {
        this.fileList = fileList;
    }
}
