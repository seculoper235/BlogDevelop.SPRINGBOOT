package com.example.blogdevelop.Domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "post")
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue
    private int id;
    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Post(int id, String title, String content, Category category, User user) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.category = category;
        this.user = user;
    }
}
