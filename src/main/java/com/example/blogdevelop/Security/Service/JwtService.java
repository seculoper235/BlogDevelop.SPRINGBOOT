package com.example.blogdevelop.Security.Service;

import com.example.blogdevelop.Domain.User;
import com.example.blogdevelop.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class JwtService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findById(username)
                .orElseThrow(NoSuchElementException::new);

        return toUser(user);
    }

    public org.springframework.security.core.userdetails.User toUser(User user) {
        return (org.springframework.security.core.userdetails.User) org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getUserAuthority().toString())
                .build();
    }
}
