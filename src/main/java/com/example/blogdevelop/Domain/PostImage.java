package com.example.blogdevelop.Domain;

import com.example.blogdevelop.Web.Setting.Dto.ImageType;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "post_image")
public class PostImage {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @Enumerated(EnumType.STRING)
    private ImageType imageType;

    @OneToOne
    @JoinColumn(name = "file_id")
    private File file;

    @Builder
    public PostImage(int id, Post post, ImageType imageType, File file) {
        this.id = id;
        this.post = post;
        this.imageType = imageType;
        this.file = file;
    }
}
