package com.example.blogdevelop.Security.Controller;

import com.example.blogdevelop.Security.Dto.OAuthResponse;
import com.example.blogdevelop.Security.Dto.RegistInfo;
import com.example.blogdevelop.Security.Service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.NotSerializableException;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/auth")
public class SecurityController {
    private final SecurityService securityService;

    // Social 인증 후, 애플리케이션 토큰을 발급받기 위한 API
    @PostMapping("/google")
    public OAuthResponse authenticationGoogle(@RequestBody String accessToken) throws NotSerializableException {
        // TODO: 토큰) OAuth의 어세스 토큰으로 유저 정보를 가져와서 스토리지에 (토큰, 유저 정보)로 저장하고, 어세스 토큰을 반납한다
        //       세션) Spring 자체의 OAuth를 이용하면 된다. 따라서 작성 필요 X
        // redirect-url으로 프론트에게 access token을 보내고, 프론트는 access token을 서버로 보낸다.
        // 서버는 받은 access token을 가지고, Social scope를 여기서 Session 객체에 저장한다(userinfo.profile에 요청을 보내서 유저 정보를 얻어온다)
        // 굳이 클라이언트에서 access token을 받아오는 이유?
        // -> 서버가 모두 처리할 수 있지만, 그렇게 되면 REST API의 의미가 사라지며 올인원 애플리케이션 구조를 띄게 된다. 따라서 REST API를 사용하려면, access token을 따로 받아야 한다
        return securityService.googleAuthentication(accessToken);
    }

    // 추가 사용자 정보를 등록하기 위한 API
    @PostMapping("/regist")
    public OAuthResponse registUserInfo(@RequestBody RegistInfo registInfo) {
        // TODO: 사용자 등록 과정으로, 인증이 완료된 후에 유저 정보를 채움
        return securityService.registerUserInfo(registInfo);
    }
}
