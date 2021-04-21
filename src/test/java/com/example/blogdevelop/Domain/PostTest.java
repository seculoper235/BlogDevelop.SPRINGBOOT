package com.example.blogdevelop.Domain;

import com.example.blogdevelop.Repository.PostRepository;
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
import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;

@DataJpaTest
@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PostTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PostRepository postRepository;

    User user;
    Category category;
    @Before
    @DisplayName("테스트에 사용할 자료를 미리 저장")
    public void beforeMethod() {
        // 테스트에 사용할 자료를 미리 저장
        user.builder()
                .id("seculoper")
                .username("seculoper")
                .password("test")
                .build();
        category.builder()
                .name("카테고리1")
                .build();
    }

    @Test
    @DisplayName("조회 테스트")
    public void selectPostTest() {
        // Given
        Post post = Post.builder()
                .title("post1")
                .content("포스트1 입니다.")
                .category(category)
                .user(user)
                .build();
        Post expectPost = entityManager.persist(post);

        // When
        Post resultPost = postRepository.findById(post.getId()).get();

        // Then
        assertEquals(expectPost, resultPost);
    }

    @Test
    @DisplayName("전체 조회 테스트")
    public void selectAllPostTest() {
        // Given
        List<Post> postList = new ArrayList<>();
        Post post1 = Post.builder()
                .title("post1")
                .content("포스트1 입니다.")
                .category(category)
                .user(user)
                .build();
        Post post2 = Post.builder()
                .title("post2")
                .content("포스트2 입니다.")
                .category(category)
                .user(user)
                .build();
        postList.add(post1);
        postList.add(post2);
        entityManager.persist(post1);
        entityManager.persist(post2);

        // When
        List<Post> resultPostList = postRepository.findAll();

        // Then
        assertEquals(postList, resultPostList);
    }

    @Test
    @DisplayName("Title 수정 테스트")
    public void updatePostTest() {
        // Given
        Post post = Post.builder()
                .title("post1")
                .content("포스트1 입니다.")
                .category(category)
                .user(user)
                .build();
        entityManager.persist(post);

        // When
        Post expectPost = postRepository.findById(post.getId()).get();
        expectPost.setTitle("post2");
        Post resultPost = entityManager.persist(expectPost);

        // Then
        assertEquals(expectPost.getTitle(), resultPost.getTitle());
    }

    @Test(expected = NoSuchElementException.class)
    @DisplayName("삭제 테스트")
    public void deletePostTest() {
        // Given
        Post post = Post.builder()
                .title("post1")
                .content("포스트1 입니다.")
                .category(category)
                .user(user)
                .build();
        entityManager.persist(post);

        // When
        postRepository.deleteById(post.getId());

        // Then
        postRepository.findById(post.getId()).get();
    }
}
