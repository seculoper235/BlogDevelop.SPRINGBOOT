package com.example.blogdevelop.Domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "user")
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class User {
    @Id
    private String id;
    private String username;
    private String password;

    private String email;
    private String description;

    @Enumerated(EnumType.STRING)
    private UserAuthority userAuthority;

    @OneToMany(mappedBy = "user")
    private List<Post> postList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<UserImage> userImageList = new ArrayList<>();

    @Builder
    public User(String id, String username, String password, String email, String description, UserAuthority userAuthority, List<Post> postList, List<UserImage> userImageList) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.description = description;
        this.userAuthority = userAuthority;
        this.postList = postList;
        this.userImageList = userImageList;
    }
}
