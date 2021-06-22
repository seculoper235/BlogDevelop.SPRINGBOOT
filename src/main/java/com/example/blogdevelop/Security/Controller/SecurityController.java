package com.example.blogdevelop.Security.Controller;

import com.example.blogdevelop.Web.Dto.UserDto;
import com.example.blogdevelop.Security.Service.OAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/auth")
public class SecurityController {
    private final OAuthService oAuthService;

    @PostMapping("/regist")
    public void registUserInfo(@RequestBody UserDto userDto) {
        // 사용자 등록 과정으로, 인증이 완료된 후에 유저 정보를 채움
    }
}
