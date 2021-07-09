package com.example.blogdevelop.Domain;

import com.example.blogdevelop.Web.Dto.ImageType;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "user_image")
public class UserImage {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
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
