package com.example.blogdevelop.Domain;

import com.example.blogdevelop.Web.Setting.Dto.ImageType;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "file")
public class File {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "file_name")
    private String name;

    @Column(name = "save_file_name")
    private String saveName;

    private String contentType;

    @Enumerated(EnumType.STRING)
    private ImageType imageType;

    private boolean deleteFlag;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @Builder
    public File(int id, String name, String saveName, String contentType, ImageType imageType, boolean deleteFlag, Post post) {
        this.id = id;
        this.name = name;
        this.saveName = saveName;
        this.contentType = contentType;
        this.imageType = imageType;
        this.deleteFlag = deleteFlag;
        this.post = post;
    }
}
