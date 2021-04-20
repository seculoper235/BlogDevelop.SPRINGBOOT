package com.example.blogdevelop.Domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "category")
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue
    private int id;
    private String name;

    @OneToMany(mappedBy = "category")
    private List<Post> postList = new ArrayList<>();

    @Builder
    public Category(int id, String name, List<Post> postList) {
        this.id = id;
        this.name = name;
        this.postList = postList;
    }
}
