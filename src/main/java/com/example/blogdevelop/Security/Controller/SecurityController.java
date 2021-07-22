package com.example.blogdevelop.Security.Controller;

import com.example.blogdevelop.Security.Dto.OAuthResponse;
import com.example.blogdevelop.Security.Dto.RegistInfo;
import com.example.blogdevelop.Security.Service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.NotSerializableException;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/auth")
public class SecurityController {
    private final SecurityService securityService;

    // Social 인증 후, 애플리케이션 토큰을 발급받기 위한 API
    @PostMapping("/google")
    public OAuthResponse authenticationGoogle(@RequestBody String accessToken) throws NotSerializableException {
        // 프론트가 별도로 access token을 받아오고, 서버는 Resource Server에서 유저 정보를 가져와 세션에 저장한다.
        return securityService.googleAuthentication(accessToken);
    }

    // 추가 사용자 정보를 등록하기 위한 API
    @PostMapping("/regist")
    public OAuthResponse registUserInfo(@RequestBody RegistInfo registInfo) {
        return securityService.registerUserInfo(registInfo);
    }

    // 로그 아웃
    @GetMapping("/logoutProc")
    public void logoutUser() {
        securityService.logoutUser();
    }

    // 테스트 메소드
    @GetMapping("/test")
    public String uploadTest() {
        return "<html lang=\"en\">\n" +
                "  <head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>파일 보내기 예제</title>\n" +
                "  </head>\n" +
                "  <body>\n" +
                "    <form name=\"form\" method=\"post\" action=\"http://localhost:8080/profile/user/user1/image\" enctype=\"multipart/form-data\">\n" +
                "      <input type=\"file\" name=\"profileImage\" multiple=\"multiple\"/>\n" +
                "      <input type=\"submit\" id=\"submit\" value=\"전송\"/>\n" +
                "    </form>\n" +
                "  </body>\n" +
                "</html>";
    }
}
