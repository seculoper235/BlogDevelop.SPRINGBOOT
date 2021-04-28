package com.example.blogdevelop.Domain;

import com.example.blogdevelop.Dto.ImageType;
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

    @Enumerated(EnumType.STRING)
    private ImageType imageType;

    @Builder
    public UserImage(int id, User user, File file, ImageType imageType) {
        this.id = id;
        this.user = user;
        this.file = file;
        this.imageType = imageType;
    }
}
