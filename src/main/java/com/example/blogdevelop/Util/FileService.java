package com.example.blogdevelop.Util;

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

    @Value("${file.upload}")
    private String absolutePath;

    // TODO 파일의 ImageType(용도)에 따라 업로드를 다르게 진행(profile, post)
    // TODO Post와 Profile에 따라 다른 upload 메소드를 진행한다(Post는 다수의 MultipartFile을 다루므로 List<> 객체로 받고, Profile은 하나이므로 단일 객체를 받는다)
    // 프로필 이미지 업로드
    public String uploadProfile(String userId, ImageType imageType, MultipartFile multipartFile) throws IOException {
        // 원본 파일 이름
        String originFilename = multipartFile.getOriginalFilename();
        // 업로드 용 파일 이름
        assert originFilename != null;
        String fileName = getFileName(originFilename);

        // FileDto 객체 생성
        String filePath = createSavePath(ImageType.PROFILE, userId, null, null);
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
    public String uploadPosts(ImageType imageType, List<MultipartFile> multipartFiles, Integer postId) throws IOException {

        for (MultipartFile multipartFile : multipartFiles) {
            // 원본 파일 이름
            String originFilename = multipartFile.getOriginalFilename();

            // 업로드 용 파일 이름
            String fileName = getFileName(originFilename);

            // FileDto 객체 생성
            String filePath = createSavePath(ImageType.PROFILE, null, null, postId);
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

    // TODO ImageType에 따라 폴더 생성이 나뉨
    private String createSavePath(ImageType imageType, String userId, Integer catId, Integer postId) {
        String filePath = imageType == ImageType.POST ? "/posts" +catId+ "/" +postId : "/profiles";

        // TODO 가입과 동시에 {user_id}/profile 폴더 생성
        if (!new File(absolutePath, userId+filePath).exists()) {
            if(!new File(absolutePath, userId+filePath).mkdir())
                throw new InvalidFileNameException(absolutePath+"/"+userId+filePath, "잘못된 파일 경로입니다.");
        }

        return filePath;
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
