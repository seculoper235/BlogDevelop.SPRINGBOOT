package com.example.blogdevelop.Domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue
    private String id;
    private String name;

    @OneToMany(mappedBy = "category")
    private List<Post> postList = new ArrayList<>();
}
