package com.example.blogdevelop.Domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "about")
public class About {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    private String content;

    @OneToMany(mappedBy = "about")
    private List<AboutImage> aboutImageList;

    @Builder
    public About(int id, String title, String content, List<AboutImage> aboutImageList) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.aboutImageList = aboutImageList;
    }
}
