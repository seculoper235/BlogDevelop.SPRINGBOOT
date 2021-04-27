package com.example.blogdevelop.Domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "user_image")
public class UserImage {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "file_id")
    private File file;

    @Builder
    public UserImage(int id, User user, File file) {
        this.id = id;
        this.user = user;
        this.file = file;
    }
}
