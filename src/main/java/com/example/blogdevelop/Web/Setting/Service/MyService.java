package com.example.blogdevelop.Web.Setting.Service;

import com.example.blogdevelop.Domain.User;
import com.example.blogdevelop.Repository.UserRepository;
import com.example.blogdevelop.Util.FileService;
import com.example.blogdevelop.Web.Setting.Dto.ImageType;
import com.example.blogdevelop.Web.Setting.Dto.Mapper.UserMapper;
import com.example.blogdevelop.Web.Setting.Dto.ProfileRequest;
import com.example.blogdevelop.Web.Setting.Dto.ProfileResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class MyService {
    private final UserRepository userRepository;
    private final FileService fileService;

    // 프로필 조회
    public ProfileResponse selectProfile(String userId) {
        // userId로 User를 조회하고, ProfileResponse에 담아 반환
        User user = userRepository.findById(userId)
                .orElseThrow(NoSuchElementException::new);

        return UserMapper.toResponse(user);
    }

    // 프로필 수정(이미지 제외)
    public ProfileResponse updateProfile(String userId, ProfileRequest profile) {
        // userId로 User를 조회하고, map()으로 특정값만 바꾼다. 그리고 ProfileResponse에 담아 반환
        User user = userRepository.findById(userId)
                .orElseThrow(NoSuchElementException::new);
        user.setUsername(profile.getNickName());
        user.setDescription(profile.getDescription());

        return UserMapper.toResponse(user);
    }

    // 프로필 이미지 수정
    public ProfileResponse updateProfileImage(String userId, MultipartFile profileImage) throws IOException {
        String fileName = fileService.uploadProfile(userId, ImageType.PROFILE, profileImage);
        ProfileResponse profile = selectProfile(userId);
        profile.setProfile(fileName);
        return profile;
    }

    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
        SecurityContextHolder.clearContext();

        if(userRepository.existsById(userId))
            throw new NoSuchElementException();
    }
}
