package com.example.blogdevelop.Util;

import com.example.blogdevelop.Repository.FileRepository;
import com.example.blogdevelop.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {
    private final FileRepository fileRepository;
    private final UserRepository userRepository;

    @Value("${file.upload}")
    private String absolutePath;

    // TODO 파일의 ContentType에 따라 업로드를 다르게 진행
    public String upload(String userId, MultipartFile multipartFile) throws IOException {
        // 원본 파일 이름
        String originFilename = multipartFile.getOriginalFilename();
        // 업로드 용 파일 이름
        String fileName = getFileName(originFilename);

        // FileDto 객체 생성
        FileDto fileDto = FileDto.builder()
                .fileName(fileName)
                .contentType(multipartFile.getContentType())
                .path("images/"+fileName)
                .build();

        // 업로드 경로를 가지고 파일을 서버에 저장
        com.example.blogdevelop.Domain.File file = saveFile(userId, multipartFile, fileDto);

        // 서버 상에 이미지가 저장된 경로를 반환
        // (서버 BASE_URL + File 엔티티.saveName)
        return file.getName();
    }

    private com.example.blogdevelop.Domain.File saveFile(String userId, MultipartFile multipartFile, FileDto fileDto) throws IOException {
        com.example.blogdevelop.Domain.File file = userRepository.findById(userId)
                .orElseThrow(NoSuchElementException::new)
                .getProfile();

        // 폴더가 없으면 생성
        if (!new File(absolutePath, userId+"/images").exists())
            new File(absolutePath, userId+"/images").mkdir();

        // 기존 파일이 존재한다면 삭제
        File origin = new File(absolutePath, userId +"/"+ file.getSaveName());
        if(origin.exists()) {
            origin.delete();
            log.info("파일 삭제 성공!");
        }

        // 지정된 경로에 파일 업로드
        multipartFile.transferTo(new File(absolutePath, userId +"/"+ fileDto.getPath()));

        // file 수정 후 DB 저장
        file.setName(fileDto.getFileName());
        file.setContentType(fileDto.getContentType());
        file.setSaveName(fileDto.getPath());
        fileRepository.save(file);

        return file;
    }

    private String getFileName(String originFilename) {
        String name = originFilename.substring(originFilename.lastIndexOf("."));
        return UUID.randomUUID().toString().concat(name);
    }
}
