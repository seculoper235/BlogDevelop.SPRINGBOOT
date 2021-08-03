package com.example.blogdevelop.Util;

import com.example.blogdevelop.Domain.Post;
import com.example.blogdevelop.Repository.FileRepository;
import com.example.blogdevelop.Repository.UserRepository;
import com.example.blogdevelop.Web.Setting.Dto.ImageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.InvalidFileNameException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {
    private final FileRepository fileRepository;
    private final UserRepository userRepository;

    @Value("${absolute.upload}")
    private String absolutePath;
    @Value("${profiles.upload}")
    private String profilePath;
    @Value("${about.upload}")
    private String aboutPath;
    @Value("${posts.upload}")
    private String postPath;

    // TODO 파일의 ImageType(용도)에 따라 업로드를 다르게 진행(profile, post)
    // TODO Post와 Profile에 따라 다른 upload 메소드를 진행한다(Post는 다수의 MultipartFile을 다루므로 List<> 객체로 받고, Profile은 하나이므로 단일 객체를 받는다)
    // 프로필 이미지 업로드
    public String uploadProfile(String userId, ImageType imageType, MultipartFile multipartFile) throws IOException {
        // 폴더 경로
        String filePath = userId+ profilePath;
        // 원본 파일 이름
        String originFilename = multipartFile.getOriginalFilename();
        // 업로드 용 파일 이름
        assert originFilename != null;
        String fileName = getFileName(originFilename);

        // FileDto 객체 생성
        FileDto fileDto = FileDto.builder()
                .fileName(fileName)
                .contentType(multipartFile.getContentType())
                .imageType(imageType)
                .path(filePath +"/"+ fileName)
                .build();

        // 업로드 경로를 가지고 파일을 서버에 저장
        com.example.blogdevelop.Domain.File file = saveProfileFile(userId, multipartFile, fileDto);

        // 서버 상에 이미지가 저장된 경로를 반환
        // (서버 BASE_URL + File 엔티티.saveName)
        return file.getName();
    }

    // TODO MultipartFile 리스트 업로드
    public String uploadPosts(ImageType imageType, List<MultipartFile> multipartFiles, String userId, int catId, int postId) throws IOException {
        // 저장 폴더 생성
        categoryFilePath(userId, catId);
        String filePath = postFilePath(userId, catId, postId);

        for (MultipartFile multipartFile : multipartFiles) {
            // 원본 파일 이름
            String originFilename = multipartFile.getOriginalFilename();

            // 업로드 용 파일 이름
            String fileName = getFileName(originFilename);

            // FileDto 객체 생성
            FileDto fileDto = FileDto.builder()
                    .fileName(fileName)
                    .contentType(multipartFile.getContentType())
                    .imageType(imageType)
                    .path(filePath +"/"+ fileName)
                    .build();

            // 업로드 경로를 가지고 파일을 서버에 저장

        }

        // 서버 상에 이미지가 저장된 경로를 반환
        // (서버 BASE_URL + File 엔티티.saveName)
        //return file.getName();
        return null;
    }

    // 회원 가입 후, 바로 생성되는 업로드 폴더들을 생성
    public void initFilePath(String userId, String filePath) {
        createFilePath(userId, null);
        createFilePath(userId, profilePath);
        createFilePath(userId, aboutPath);
        createFilePath(userId, postPath);
    }

    // 포스트 생성 시 실행되는 메소드
    private String categoryFilePath(String userId, int catId) {
        String path = postPath+ "/" +catId;
        return createFilePath(userId, path);
    }

    // 포스트 생성 시 실행되는 메소드
    public String postFilePath(String userId, int catId, int postId) {
        String path = postPath+ "/" +catId+ "/" +postId;
        return createFilePath(userId, path);
    }

    private String createFilePath(String userId, String filePath) {
        if (!new File(absolutePath, userId+filePath).exists()) {
            if(!new File(absolutePath, userId+filePath).mkdir())
                throw new InvalidFileNameException(absolutePath+"/"+userId+filePath, "잘못된 파일 경로입니다.");
        }

        return filePath;
    }

    // 포스트 파일 하나를 저장
    private com.example.blogdevelop.Domain.File savePostFiles(String catId, String postImageId, MultipartFile multipartFile, FileDto fileDto) {
        // PostImage 테이블에서 파일들 조회

        return null;
    }

    private com.example.blogdevelop.Domain.File saveProfileFile(String userId, MultipartFile multipartFile, FileDto fileDto) throws IOException {
        com.example.blogdevelop.Domain.File file = userRepository.findById(userId)
                .orElseThrow(NoSuchElementException::new)
                .getProfile();

        // 기존 파일이 존재한다면 삭제
        File origin = new File(absolutePath, userId + file.getSaveName());
        if(origin.exists()) {
            origin.delete();
            log.info("파일 삭제 성공!");
        }

        // 지정된 경로에 파일 업로드
        // TODO 만약 이 과정에서 없다면 폴더를 생성(오류를 이용해 처리하는 것은 좋지 못한 방법이다. 제대로 처리할 수 없기 때문)
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
