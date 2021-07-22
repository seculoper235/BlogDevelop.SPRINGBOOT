package com.example.blogdevelop.Util;

import lombok.Builder;
import lombok.Getter;

@Getter
public class FileDto {
    private final String fileName;
    private final String contentType;
    private final String path;

    @Builder
    public FileDto(String fileName, String contentType, String path) {
        this.fileName = fileName;
        this.contentType = contentType;
        this.path = path;
    }
}
