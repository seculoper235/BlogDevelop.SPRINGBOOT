package com.example.blogdevelop.Domain;

import com.example.blogdevelop.Repository.FileRepository;
import com.example.blogdevelop.Repository.UserImageRepository;
import com.example.blogdevelop.Repository.UserRepository;
import com.example.blogdevelop.Web.Dto.ImageType;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Slf4j
public class UserImageTest {
    @Autowired
    private TestEntityManager tem;

    @Autowired
    private UserImageRepository userImageRepository;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private UserRepository userRepository;

    private File temp;
    User user;

    @Before
    public void setUp() {
        // File 에다 데이터를 저장함
        temp = File.builder()
                .name("이미지1.jpg")
                .saveName("/resources/static/이미지1.jpg")
                .contentType("image/jpeg")
                .build();

        List<Post> postList = new ArrayList<>();
        postList.add(Post.builder()
                .title("post1")
                .build());

        User test = User.builder()
                .id("seculoper")
                .description("취미가 개발인 수학자")
                .username("seculoper")
                .password("test")
                .email("asdf1234@naver.com")
                .userAuthority(UserAuthority.USER)
                .postList(postList)
                .build();
        user = userRepository.save(test);
    }

    @DisplayName("프로필 사진 조회")
    @Test
    public void selectProfileTest() {
        // Given
        File file = fileRepository.save(temp);

        UserImage testImage = UserImage.builder()
                .user(user)
                .file(file)
                .imageType(ImageType.PROFILE)
                .build();
        userImageRepository.save(testImage);

        // When
        UserImage resultImage = userImageRepository.findByUserId(user.getId());

        // then
        assertEquals("이미지1.jpg", resultImage.getFile().getName());
        assertEquals("/resources/static/이미지1.jpg", resultImage.getFile().getSaveName());
        assertEquals("image/jpeg", resultImage.getFile().getContentType());
        assertEquals(ImageType.PROFILE, resultImage.getImageType());
        assertEquals(UserAuthority.USER, user.getUserAuthority());
    }

    @DisplayName("프로필 사진 저장")
    @Test
    public void saveProfileTest() {
        // When
        File file = fileRepository.save(temp);

        UserImage expectedImage = UserImage.builder()
                .file(file)
                .imageType(ImageType.PROFILE)
                .build();
        UserImage resultImage = userImageRepository.save(expectedImage);

        // Then
        assertEquals("이미지1.jpg", resultImage.getFile().getName());
        assertEquals("/resources/static/이미지1.jpg", resultImage.getFile().getSaveName());
        assertEquals("image/jpeg", resultImage.getFile().getContentType());
        assertEquals(ImageType.PROFILE, resultImage.getImageType());
    }
}