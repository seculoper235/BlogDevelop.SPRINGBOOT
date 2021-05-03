package com.example.blogdevelop.Domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "user")
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class User {
    @Id
    private String id;
    private String username;
    private String password;

    private String description;

    @OneToMany(mappedBy = "user")
    private List<Post> postList = new ArrayList<>();

    @OneToMany(mappedBy = "user_image")
    private List<UserImage> userImageList = new ArrayList<>();

    @Builder
    public User(String id, String username, String password, String description, List<Post> postList,  List<UserImage> userImageList) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.description = description;
        this.postList = postList;
        this.userImageList = userImageList;
    }
}
