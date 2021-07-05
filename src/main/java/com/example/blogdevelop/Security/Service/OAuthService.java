package com.example.blogdevelop.Security.Service;

import com.example.blogdevelop.Domain.User;
import com.example.blogdevelop.Repository.UserRepository;
import com.example.blogdevelop.Security.Dto.Mapper.UserMapper;
import com.example.blogdevelop.Security.Dto.OAuthUserDto;
import com.example.blogdevelop.Security.Dto.SessionUser;
import com.example.blogdevelop.Security.Dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
public class OAuthService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delagate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delagate.loadUser(request);

        String nameAttributeKey = request.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        // DTO로 변환
        UserDto userDto = new UserDto(oAuth2User, nameAttributeKey);

        // DB 저장
        userRepository.save(UserMapper.toEntity(userDto));

        // 세션 저장
        httpSession.setAttribute("googleUser", new SessionUser.Google(userDto));

        // Default 반환
        return new DefaultOAuth2User(
                oAuth2User.getAuthorities(),
                userDto.getAttributes(),
                userDto.getNameAttributeKey()
        );
    }
}
