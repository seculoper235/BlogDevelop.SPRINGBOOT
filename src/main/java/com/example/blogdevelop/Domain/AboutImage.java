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
@Table(name = "about_image")
public class AboutImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "about_id")
    private About about;

    @OneToOne
    @JoinColumn(name = "file_id")
    private File file;

    @Builder
    public AboutImage(int id, About about, File file) {
        this.id = id;
        this.about = about;
        this.file = file;
    }
}
