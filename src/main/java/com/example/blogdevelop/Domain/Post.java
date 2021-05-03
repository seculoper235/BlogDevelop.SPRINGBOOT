package com.example.blogdevelop.Domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Table(name = "post")
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "post")
    private List<PostImage> postImageList;

    @Builder
    public Post(int id, String title, String content, Category category, User user, List<PostImage> postImageList) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.category = category;
        this.user = user;
        this.postImageList = postImageList;
    }
}
