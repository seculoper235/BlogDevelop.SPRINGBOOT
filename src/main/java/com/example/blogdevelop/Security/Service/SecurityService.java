package com.example.blogdevelop.Security.Service;

import com.example.blogdevelop.Domain.User;
import com.example.blogdevelop.Repository.UserRepository;
import com.example.blogdevelop.Security.Dto.Mapper.InfoMapper;
import com.example.blogdevelop.Security.Dto.OAuthResponse;
import com.example.blogdevelop.Security.Dto.RegistInfo;
import com.example.blogdevelop.Security.Dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class SecurityService {
    // 의존성 주입
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    public OAuthResponse registerUserInfo(RegistInfo registInfo) {
        // TODO: User로 toEntity 후 저장
        SessionUser.Google googleUser = (SessionUser.Google) httpSession.getAttribute("googleUser");
        if(googleUser == null) {
            throw new NoSuchElementException();
        }
        registInfo.setUpInfo(googleUser);

        User user = userRepository.save(InfoMapper.toEntity(registInfo));
        return OAuthResponse.builder()
                .nickName(user.getId())
                .isRegistered(true)
                .build();
    }

    // redirect-url으로 프론트에게 access token을 보내고, 프론트는 access token을 서버로 보낸다.
    // TODO: 서버는 받은 access token을 가지고, Social scope를 여기서 Session 객체에 저장한다(userinfo.profile에 요청을 보내서 유저 정보를 얻어온다)
    public OAuthResponse googleAuthetication(String accessToken) {
        // 유저 정보를 가져옴

        // 가져온 정보를 세션 객체에 담아서 세션에 저장

        // OAuthResponse에 요소를 채워서 반환(isRegistered=false)
        return new OAuthResponse();
    }

    public void userInfo(String accessToken) {
        // 유저 정보를 가져옴
    }
}
