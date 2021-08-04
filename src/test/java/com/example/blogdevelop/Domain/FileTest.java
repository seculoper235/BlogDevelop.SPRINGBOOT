package com.example.blogdevelop.Domain;

import com.example.blogdevelop.Repository.FileRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

@DataJpaTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
public class FileTest {
    @Autowired
    private TestEntityManager tem;

    @Autowired
    private FileRepository fileRepository;

    private File file;

    @Before
    public void setUp() {
        file = File.builder()
                .name("파일1")
                .saveName("/user/file/파일1")
                .contentType("img")
                .build();
    }

    @DisplayName("한건 조회 테스트")
    @Test
    public void selectFile() {
        // Given
        File expectedFile = tem.persist(file);

        // When
        File resultFile = fileRepository.findByName(file.getName());

        // Then
        assertEquals(expectedFile, resultFile);
    }

    @DisplayName("수정 테스트(조회 테스트를 먼저 진행할 것!)")
    @Test
    public void saveFile() {
        // Given
        File expectedFile = tem.persist(file);

        // When
        File tempFile = fileRepository.findByName(file.getName());
        tempFile.setName("파일2");
        File resultFile = fileRepository.save(tempFile);

        // Then
        assertEquals(expectedFile.getId(), resultFile.getId());
        assertEquals("파일2", resultFile.getName());
        assertEquals(expectedFile.getSaveName(), resultFile.getSaveName());
        assertEquals(expectedFile.getContentType(), resultFile.getContentType());
        assertEquals(expectedFile.isDeleteFlag(), resultFile.isDeleteFlag());
    }

    @DisplayName("삭제 테스트(저장 테스트를 먼저 진행할 것!)")
    @Test(expected = NoSuchElementException.class)
    public void deleteFile() {
        // Given
        File expectFile = fileRepository.save(file);

        // When
        fileRepository.deleteById(file.getId());

        // Then
        fileRepository.findById(file.getId()).get();
    }
}
