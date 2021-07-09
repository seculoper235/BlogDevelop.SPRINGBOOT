package com.example.blogdevelop.Security.Service;

import com.example.blogdevelop.Domain.User;
import com.example.blogdevelop.Repository.UserRepository;
import com.example.blogdevelop.Security.Dto.Mapper.InfoMapper;
import com.example.blogdevelop.Security.Dto.OAuthResponse;
import com.example.blogdevelop.Security.Dto.RegistInfo;
import com.example.blogdevelop.Security.Dto.SessionUser;
import com.example.blogdevelop.Security.Dto.UserDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.io.NotSerializableException;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@PropertySource({"classpath:application-oauth.properties"})
public class SecurityService {
    // 의존성 주입
    @Value("${spring.security.oauth2.client.provider.google.user-info-uri}")
    private String GOOGLE_USERINFO;
    private final UserRepository userRepository;
    private final HttpSession httpSession;
    private final ObjectMapper objectMapper;

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
    public OAuthResponse googleAuthentication(String accessToken) throws NotSerializableException {
        // 유저 정보를 가져옴
        UserDto userDto = userInfo(accessToken);

        // 가져온 정보를 세션 객체에 담아서 세션에 저장
        httpSession.setAttribute("googleUser", new SessionUser.Google(userDto));

        // OAuthResponse에 요소를 채워서 반환(isRegistered=false)
        return OAuthResponse.builder()
                .isRegistered(false)
                .oAuthToken(accessToken)
                .build();
    }

    public UserDto userInfo(String accessToken) throws NotSerializableException {
        // 유저 정보를 가져옴
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        headers.set("Authorization", "Bearer" + accessToken);
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        String tokenBody = restTemplate.exchange(GOOGLE_USERINFO, HttpMethod.POST, httpEntity, String.class).getBody();
        System.out.println("TokenBody = " + tokenBody);

        JsonNode node;
        try {
            node = objectMapper.readTree(tokenBody);

            return UserDto.builder()
                    .username(node.get("picture").asText())
                    .email(node.get("email").asText())
                    .image(node.get("picture").asText())
                    .build();
        } catch (JsonProcessingException e) {
            throw new NotSerializableException();
        }
    }
}
