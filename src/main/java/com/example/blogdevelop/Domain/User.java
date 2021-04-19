package com.example.blogdevelop.Domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "user")
public class User {
    @Id @GeneratedValue
    private String id;
    private String username;
    private String password;

    private String image;
    private String description;

    @OneToMany(mappedBy = "post")
    private List<Post> postList = new ArrayList<>();
}
