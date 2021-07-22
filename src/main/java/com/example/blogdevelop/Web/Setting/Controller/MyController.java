package com.example.blogdevelop.Web.Setting.Controller;

import com.example.blogdevelop.Web.Setting.Dto.ProfileRequest;
import com.example.blogdevelop.Web.Setting.Dto.ProfileResponse;
import com.example.blogdevelop.Web.Setting.Service.MyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/profile")
public class MyController {
    private final MyService myService;

    // 프로필 조회
    @GetMapping("/user/{id}")
    public ProfileResponse selectProfile(@PathVariable(name = "id") String userId) {
        return myService.selectProfile(userId);
    }

    // 프로필 변경
    @PutMapping("/user/{id}")
    public ProfileResponse updateProfile(@PathVariable(name = "id") String userId, @RequestBody ProfileRequest profileRequest) {
        return myService.updateProfile(userId, profileRequest);
    }

    // 프로필 이미지 변경
    @PostMapping("/user/{id}/image")
    public ProfileResponse updateProfileImage(@PathVariable(name = "id") String userId, @RequestParam @NotNull MultipartFile profileImage) throws IOException {
        return myService.updateProfileImage(userId, profileImage);
    }

    // 회원 탈퇴
    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable(name = "id") String userId) {
        myService.deleteUser(userId);
    }
}
