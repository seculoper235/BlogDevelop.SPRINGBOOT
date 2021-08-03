package com.example.blogdevelop.Service;

import com.example.blogdevelop.Domain.User;
import com.example.blogdevelop.Repository.FileRepository;
import com.example.blogdevelop.Repository.PostRepository;
import com.example.blogdevelop.Repository.UserRepository;
import com.example.blogdevelop.Util.FileDto;
import com.example.blogdevelop.Web.Setting.Dto.ImageType;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
public class FileServiceTest {
    @MockBean
    private FileRepository fileRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PostRepository postRepository;

    private String absolutePath = "/tmp/uploads/";

    String userId = "user1";

    @Before
    @DisplayName("유저 폴더 미리 생성")
    public void init() {
        new File("/tmp/uploads/user1").mkdir();
    }

    @Test
    @DisplayName("기초 폴더(유저, 소개, 프로필 파일 저장 폴더) 생성 테스트")
    public void create_Init_Directory_Test() {
        // 기대 경로
        String expectedProfilePath = "/tmp/uploads/user1/profiles";

        // 결과 경로
        String resultProfilePath = "/profiles";

        // 폴더 생성 코드 실행
        if (!new File(absolutePath, userId + resultProfilePath).exists()) {
            System.out.println("프로필 폴더 생성");
            if(!new File(absolutePath, userId + resultProfilePath).mkdir()) {
                System.out.println("프로필 폴더 생성 실패!");
            }
        }

        assertTrue("프로필 폴더 생성 성공!", new File(expectedProfilePath).exists());
    }

    @Test
    @DisplayName("카테고리 폴더 생성 테스트")
    public void create_Cat_Directory_Test() {
        // 포스트 폴더 생성 파라미터(유저 Id, 카테고리 Id)
        String catId = "cat_ID";

        // 기대 경로
        String expectedPath = "/tmp/uploads/user1/posts/cat_ID";

        // 결과 경로
        String resultPath = "/posts/" +catId;

        // 폴더 생성 코드 실행
        File resultFIle = new File(absolutePath, userId + resultPath);
        if (!new File(absolutePath, userId + resultPath).exists()) {
            System.out.println("카테고리 폴더 생성");
            if(!new File(absolutePath, userId + resultPath).mkdir()) {
                System.out.println("카테고리 폴더 생성 실패!");
            }
        }

        assertTrue(new File(expectedPath).exists());
    }

    @Test
    @DisplayName("포스트 폴더 생성 테스트")
    public void create_Post_Directory_Test() {
        // 포스트 폴더 생성 파라미터(유저 Id, 카테고리 Id, 포스트 Id)
        String catId = "cat_ID";
        String postId = "post_ID";

        // 기대 경로
        String expectedPath = "/tmp/uploads/user1/posts/cat_ID/post_ID";

        // 결과 경로
        String resultPath = "/posts/" +catId+ "/" +postId;

        // 폴더 생성 코드 실행
        File resultFIle = new File(absolutePath, userId + resultPath);
        if (!new File(absolutePath, userId + resultPath).exists()) {
            System.out.println("포스트 폴더 생성");
            if(!new File(absolutePath, userId + resultPath).mkdir()) {
                System.out.println("포스트 폴더 생성 실패!");
            }
        }

        assertTrue(new File(expectedPath).exists());
    }


    @Test
    @DisplayName("프로필 업로드 테스트")
    public void upload_Profile_Test() {
        //
    }

    @Test
    @DisplayName("포스트 파일들 업로드 테스트")
    public void upload_PostFiles_Test() {
        //
    }
}
