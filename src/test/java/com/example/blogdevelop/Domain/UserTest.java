package com.example.blogdevelop.Domain;

import com.example.blogdevelop.Repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;

// Replace.NONE으로 설정하여, Springboot 자체 DB가 아닌 내가 설정한 DB를 사용
@DataJpaTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserTest {
    @Autowired
    public TestEntityManager entityManager;

    @Autowired
    public UserRepository userRepository;

    List<Post> postList = new ArrayList<>();
    @Before
    @DisplayName("테스트에 사용할 자료를 미리 저장")
    public void beforeMethod() {
        postList.add(Post.builder()
                .title("post1")
                .build());
    }

    @Test
    @DisplayName("조회 테스트")
    public void selectUserTest() {
        // Given
        User user = User.builder()
                .id("seculoper")
                .description("취미가 개발인 수학자")
                .username("seculoper")
                .password("test")
                .postList(postList)
                .build();
        // 조회라는 기능에 집중하기 위해, save가 아닌 persist 사용
        entityManager.persist(user);

        // When
        User testUser = userRepository.findById("seculoper").get();

        //Then
        assertEquals(user, testUser);
    }

    @Test
    @DisplayName("전체 조회 테스트")
    public void selectAllUserTest() {
        // Given
        List<User> userList = new ArrayList<>();
        User user1 = User.builder()
                .id("mathematician")
                .username("mathematician")
                .password("math")
                .postList(postList)
                .build();
        User user2 = User.builder()
                .id("seculoper")
                .username("seculoper")
                .password("test")
                .postList(postList)
                .build();
        userList.add(user1);
        userList.add(user2);
        entityManager.persist(user1);
        entityManager.persist(user2);

        // When
        List<User> testUserList = userRepository.findAll();

        // Then
        assertEquals(userList, testUserList);
    }

    @Test
    @DisplayName("username 수정 테스트(조회 테스트를 먼저 진행할 것!!)")
    public void updateUserTest() {
        // Given
        User user = User.builder()
                .id("seculoper")
                .description("취미가 개발인 수학자")
                .username("seculoper")
                .password("test")
                .postList(postList)
                .build();
        userRepository.save(user);

        // When
        User resultUser = userRepository.findById(user.getId()).get();
        resultUser.builder()
                .username("mathematician")
                .build();
        User testUser = entityManager.persist(resultUser);

        //Then
        assertEquals(resultUser.getUsername(), testUser.getUsername());
    }

    @Test(expected = NoSuchElementException.class)
    @DisplayName("삭제 테스트")
    public void deleteUserTest() {
        // Given
        User user = User.builder()
                .id("seculoper")
                .username("seculoper")
                .password("test")
                .postList(postList)
                .build();
        entityManager.persist(user);

        // When
        userRepository.deleteById("seculoper");

        //Then
        userRepository.findById("seculoper").get();
    }
}
