package com.example.blogdevelop.Util;

import com.example.blogdevelop.Domain.File;

public class FileMapper {

    public static File toEntity(FileDto fileDto) {
        return File.builder()
                .name(fileDto.getFileName())
                .saveName(fileDto.getPath())
                .contentType(fileDto.getContentType())
                .build();
    }
}
