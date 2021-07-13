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

    private String email;
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "authority")
    private UserAuthority userAuthority;

    @OneToMany(mappedBy = "user")
    private List<Post> postList = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "file_id")
    private File profile;

    @Builder
    public User(String id, String username, String password, String email, String description, UserAuthority userAuthority, List<Post> postList, File profile) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.description = description;
        this.userAuthority = userAuthority;
        this.postList = postList;
        this.profile = profile;
    }
}
