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
    @Id @GeneratedValue
    private String id;
    private String username;
    private String password;

    private String image;
    private String description;

    @OneToMany(mappedBy = "user")
    private List<Post> postList = new ArrayList<>();

    @Builder
    public User(String id, String username, String password, String image, String description, List<Post> postList) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.image = image;
        this.description = description;
        this.postList = postList;
    }
}
