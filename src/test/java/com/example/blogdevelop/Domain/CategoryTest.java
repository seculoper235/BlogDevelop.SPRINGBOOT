package com.example.blogdevelop.Domain;

import com.example.blogdevelop.Repository.CategoryRepository;
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
public class CategoryTest {
    @Autowired
    public TestEntityManager entityManager;

    @Autowired
    public CategoryRepository categoryRepository;

    List<Post> postList = new ArrayList<>();
    @Before
    @DisplayName("테스트에 사용할 자료를 미리 저장")
    public void beforeMethod() {
        // 테스트에 사용할 자료를 미리 저장
        postList.add(Post.builder()
                .title("post1")
                .build());
    }

    @Test
    @DisplayName("단일 조회 테스트")
    public void selectCategoryTest() {
        // Given
        Category category = Category.builder()
                .name("카테고리1")
                .postList(postList)
                .build();
        // 조회라는 기능에 집중하기 위해, save가 아닌 persist 사용
        entityManager.persist(category);

        // When
        Category testCategory = categoryRepository.findById(category.getId()).get();

        //Then
        assertEquals(testCategory, category);
    }

    @Test
    @DisplayName("전체 조회 테스트")
    public void selectAllCategoryTest() {
        // Given
        List<Category> categoryList = new ArrayList<>();
        Category category1 = Category.builder()
                .name("카테고리1")
                .postList(postList)
                .build();
        Category category2 = Category.builder()
                .name("카테고리2")
                .postList(postList)
                .build();
        categoryList.add(category1);
        categoryList.add(category2);
        entityManager.persist(category1);
        entityManager.persist(category2);

        // When
        List<Category> testCategoryList = categoryRepository.findAll();

        //Then
        assertEquals(testCategoryList, categoryList);
    }

    @Test
    @DisplayName("name 수정 테스트(조회 테스트를 먼저 진행할 것!)")
    public void updateCategoryTest() {
        // Given
        Category category = Category.builder()
                .name("카테고리1")
                .postList(postList)
                .build();
        Category resultCategory = categoryRepository.save(category);

        // When
        Category updateCategory = categoryRepository.findById(category.getId()).get();
        updateCategory.setName("카테고리2");
        Category testCategory = categoryRepository.save(updateCategory);

        //Then
        assertEquals(updateCategory.getName(), testCategory.getName());
    }

    @Test(expected = NoSuchElementException.class)
    @DisplayName("삭제 테스트(조회 조장 테스트 이후 진행할 것!)")
    public void deleteCategoryTest() {
        // Given
        Category category = Category.builder()
                .name("카테고리1")
                .postList(postList)
                .build();
        categoryRepository.save(category);

        // When
        categoryRepository.deleteById(category.getId());

        // Then
        categoryRepository.findById(category.getId()).get();
    }
}
