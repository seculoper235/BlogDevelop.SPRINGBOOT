package com.example.blogdevelop.Domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    private String description;

    @OneToMany(mappedBy = "user")
    private List<Post> postList = new ArrayList<>();

    @OneToOne(mappedBy = "user")
    private UserImage userImage;

    @Builder
    public User(String id, String username, String password, String description, List<Post> postList,  UserImage userImage) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.description = description;
        this.postList = postList;
        this.userImage = userImage;
    }
}
