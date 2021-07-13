package com.example.blogdevelop.Domain;

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

    private int deleteFlag;

    @Builder
    public File(int id, String name, String saveName, String contentType, int deleteFlag) {
        this.id = id;
        this.name = name;
        this.saveName = saveName;
        this.contentType = contentType;
        this.deleteFlag = deleteFlag;
    }
}
