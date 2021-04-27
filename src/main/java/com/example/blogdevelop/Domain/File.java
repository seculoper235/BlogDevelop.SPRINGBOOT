package com.example.blogdevelop.Domain;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
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

    private int deleteFlag;

    private LocalDateTime createAt;

    @Builder
    public File(int id, String name, String saveName, String contentType, int deleteFlag, LocalDateTime createAt) {
        this.id = id;
        this.name = name;
        this.saveName = saveName;
        this.contentType = contentType;
        this.deleteFlag = deleteFlag;
        this.createAt = createAt;
    }
}
