package com.example.blogdevelop.Util;

import com.example.blogdevelop.Domain.Post;
import com.example.blogdevelop.Web.Setting.Dto.ImageType;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FileDto {
    private final String fileName;
    private final String contentType;
    private final ImageType imageType;
    private final String path;
    private final Post post;

    @Builder
    public FileDto(String fileName, String contentType, ImageType imageType, String path, Post post) {
        this.fileName = fileName;
        this.contentType = contentType;
        this.imageType = imageType;
        this.path = path;
        this.post = post;
    }
}
