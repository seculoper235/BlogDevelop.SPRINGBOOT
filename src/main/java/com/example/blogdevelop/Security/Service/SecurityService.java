package com.example.blogdevelop.Security.Service;

import com.example.blogdevelop.Domain.User;
import com.example.blogdevelop.Repository.UserRepository;
import com.example.blogdevelop.Security.Config.JwtProvider;
import com.example.blogdevelop.Security.Dto.Mapper.InfoMapper;
import com.example.blogdevelop.Security.Dto.OAuthResponse;
import com.example.blogdevelop.Security.Dto.RegistInfo;
import com.example.blogdevelop.Security.Dto.UserDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.NotSerializableException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@PropertySource({"classpath:application-oauth.properties"})
public class SecurityService {
    // 의존성 주입
    @Value("${spring.security.oauth2.client.provider.google.user-info-uri}")
    private String GOOGLE_USERINFO;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final HashMap<String, UserDto> oAuthStorage;
    private final JwtProvider jwtProvider;

    /* Redis 대신에 임시 저장소를 사용하고, 세션 ID 대신에 access token을 사용 */

    public OAuthResponse registerUserInfo(RegistInfo registInfo) {
        // access token을 활용하여 임시 저장소에서 OAuth 유저 정보를 가져옴
        UserDto userDto = oAuthStorage.get(registInfo.getAccessToken());

        // OAuth 유저 정보를 RegistInfo 에다 집어넣음
        registInfo.setUpInfo(userDto);

        // RegistInfo를 User 엔티티에다 넣고 DB 저장
        User user = userRepository.save(InfoMapper.toEntity(registInfo));

        // createJwtToken() 실행
        return createJwtToken(user.getUsername());
    }

    public OAuthResponse googleAuthentication(String accessToken) throws NotSerializableException {
        // 유저 정보를 가져옴
        UserDto userDto = userInfo(accessToken);

        // 저장된 User 정보가 있는지 확인
        // 있다면 이미 인증된 것 이므로, createJwtToken() 실행
        if(isRegistered(userDto.getUsername()))
            return createJwtToken(userDto.getUsername());

        // 없다면 미인증 이므로, preAuthentication() 실행
        return preAuthentication(userDto, accessToken);
    }

    private boolean isRegistered(String username) {
        return userRepository.existsById(username);
    }

    private OAuthResponse createJwtToken(String username) {
        // JwtProvider 구현
        List<String> authorities = userRepository.findById(username).stream()
                .map(e -> e.getUserAuthority().name())
                .collect(Collectors.toList());
        String jwtToken = jwtProvider.createJwt(username, authorities);

        return OAuthResponse.builder()
                .nickName(username)
                .isRegistered(true)
                .jwtToken(jwtToken)
                .build();
    }

    private OAuthResponse preAuthentication(UserDto userDto, String accessToken) {
        // 세션 ID가 아닌 access token을 활용하여 OAuth 유저 정보를 임시 저장
        oAuthStorage.put(accessToken, userDto);

        // JWT를 생성하지 않고, OAuth Access Token 반환()
        return OAuthResponse.builder()
                .nickName(userDto.getUsername())
                .isRegistered(false)
                .oAuthToken(accessToken)
                .build();
    }

    private UserDto userInfo(String accessToken) throws NotSerializableException {
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
                    .username(node.get("sub").asText())
                    .email(node.get("email").asText())
                    .image(node.get("picture").asText())
                    .build();
        } catch (JsonProcessingException e) {
            throw new NotSerializableException();
        }
    }

    public void logoutUser() {
        if(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)
            log.error("토큰이 존재하지 않습니다");
        else {
            SecurityContextHolder.clearContext();
            log.info("로그아웃 되었습니다");
        }
    }
}
