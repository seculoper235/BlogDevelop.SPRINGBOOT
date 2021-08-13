package com.example.blogdevelop.Util;

import com.example.blogdevelop.Domain.Post;
import com.example.blogdevelop.Domain.User;
import com.example.blogdevelop.Repository.FileRepository;
import com.example.blogdevelop.Repository.PostRepository;
import com.example.blogdevelop.Repository.UserRepository;
import com.example.blogdevelop.Security.Dto.RegistInfo;
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
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {
    private final FileRepository fileRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Value("${absolute.upload}")
    private String absolutePath;
    @Value("${profiles.upload}")
    private String profilePath;
    @Value("${about.upload}")
    private String aboutPath;
    @Value("${posts.upload}")
    private String postPath;


    // 프로필 이미지 업로드
    public String uploadProfile(String userId, ImageType imageType, MultipartFile multipartFile) throws IOException {
        // 폴더 경로
        String filePath = profileFilePath(userId);
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
        return file.getName();
    }

    // TODO MultipartFile 리스트 업로드
    public List<String> uploadPosts(ImageType imageType, List<MultipartFile> multipartFiles, String userId, int catId, int postId) throws IOException {
        // 저장 폴더 생성
        String filePath = postFilePath(userId, postId);

        // 대상 포스트 조회
        Post post = postRepository.findById(postId)
                .orElseThrow();

        // 업로드 된 File 들을 담을 리스트 생성
        List<com.example.blogdevelop.Domain.File> uploadFileList = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFiles) {
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
                    .post(post)
                    .build();

            // 업로드 경로를 가지고 파일을 서버에 저장
            com.example.blogdevelop.Domain.File uploadFile = savePostFile(multipartFile, fileDto);

            // 리스트에 추가
            uploadFileList.add(uploadFile);
        }

        // 서버 상에 이미지가 저장된 경로를 반환
        return uploadFileList.stream()
                .map(com.example.blogdevelop.Domain.File::getName)
                .collect(Collectors.toList());
    }

    // 프로필 업데이트 시 실행되는 메소드
    public String profileFilePath(String userId) {
        return createFilePath(userId, profilePath);
    }

    // 포스트 생성 시 실행되는 메소드
    public String postFilePath(String userId, int postId) {
        String path = postPath+ "/" +postId;
        return createFilePath(userId, path);
    }

    // 소개 페이지 작성 시 실행되는 메소드
    public String aboutFilePath(String userId) {
        return createFilePath(userId, aboutPath);
    }

    private String createFilePath(String userId, String filePath) {
        String dirPath = userId+filePath;
        if (!new File(absolutePath, dirPath).exists()) {
            if(!new File(absolutePath, dirPath).mkdirs())
                throw new InvalidFileNameException(absolutePath+"/"+userId+filePath, "잘못된 파일 경로입니다.");
        }

        return dirPath;
    }

    public com.example.blogdevelop.Domain.File saveOAuthProfile(RegistInfo registInfo) {
        com.example.blogdevelop.Domain.File file = com.example.blogdevelop.Domain.File.builder()
                .name(registInfo.getProfile())
                .saveName(registInfo.getProfile())
                .contentType("image/jpeg")
                .imageType(ImageType.PROFILE)
                .build();
        return fileRepository.save(file);
    }

    private com.example.blogdevelop.Domain.File saveProfileFile(String userId, MultipartFile multipartFile, FileDto fileDto) throws IOException {
        User user = userRepository.findById(userId)
                .orElseThrow(NoSuchElementException::new);

        com.example.blogdevelop.Domain.File file = user.getProfile();

        // 기존 파일이 존재한다면 삭제
        File origin = new File(absolutePath, file.getSaveName());
        if(origin.exists()) {
            origin.delete();
            log.info("파일 삭제 성공!");
        }

        // 지정된 경로에 파일 업로드
        multipartFile.transferTo(new File(absolutePath, fileDto.getPath()));

        // file 수정 후 DB 저장
        file.setName(fileDto.getFileName());
        file.setContentType(fileDto.getContentType());
        file.setSaveName(fileDto.getPath());
        fileRepository.save(file);

        return file;
    }

    // 포스트 파일 하나를 저장
    private com.example.blogdevelop.Domain.File savePostFile(MultipartFile multipartFile, FileDto fileDto) throws IOException {
        // fileDto를 가지고, 지정된 경로에 멀티 파일 업로드
        multipartFile.transferTo(new File(absolutePath, fileDto.getPath()));

        // FileDto를 File 엔티티로 변환 후 저장
        com.example.blogdevelop.Domain.File file = FileMapper.toEntity(fileDto);

        return fileRepository.save(file);
    }

    // 업로드 삭제 버튼 클릭 시, 실행되는 메소드
    public com.example.blogdevelop.Domain.File setDeleteFlag(String filename) {
        // deleteFlag = 1로 바꿈
        com.example.blogdevelop.Domain.File file = fileRepository.findByName(filename);
        file.setDeleteFlag(true);

        return fileRepository.save(file);
    }

    // 포스트 저장 후, 바로 실행되는 업로드 파일 삭제 메소드
    public void deletePostFile(int postId) {
        // 현재 존재하는 파일 리스트
        List<com.example.blogdevelop.Domain.File> fileList = fileRepository.findAllByPostId(postId);

        // 파일이 존재한다면, 삭제 대상인 파일들을 모두 삭제
        if(!fileList.isEmpty()) {
            // 테이블 데이터 모두 삭제
            fileRepository.deleteAllByPost_Id(postId);

            // 해당 업로드 파일을 파일 경로에서 삭제
            for (com.example.blogdevelop.Domain.File file : fileList) {
                if(!new File(absolutePath, file.getSaveName()).delete())
                    throw new InvalidFileNameException(absolutePath+file.getSaveName(), "잘못된 파일 경로입니다.");
            }
        }
    }

    // 파라미터: postId, userId
    public void deletePostDir(int postId) {
        // 해당 post 업로드 폴더 경로 얻어냄
        Post post = postRepository.findById(postId).orElseThrow();
        String postPath = postFilePath(post.getUser().getId(), postId);

        // 폴더 삭제
        if(!new File(absolutePath, postPath).delete())
            throw new InvalidFileNameException(absolutePath+postPath, "잘못된 파일 경로입니다.");
    }

    private String getFileName(String originFilename) {
        String name = originFilename.substring(originFilename.lastIndexOf("."));
        return UUID.randomUUID().toString().concat(name);
    }
}
