package com.example.blogdevelop.Service;

import com.example.blogdevelop.Repository.FileRepository;
import com.example.blogdevelop.Repository.PostRepository;
import com.example.blogdevelop.Repository.UserRepository;
import com.example.blogdevelop.Web.Setting.Dto.ImageType;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

import static org.junit.Assert.assertFalse;
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

    @Test
    @DisplayName("프로필 폴더 생성 테스트")
    public void create_Profile_Directory_Test() {
        // 프로필 폴더 생성 파라미터(유저 Id, 이미지 타입)
        String userId = "user1";
        String catId = "";
        String postId = "";

        // 기대 경로
        String expectedPath = "/tmp/uploads/user1/profiles";

        // 결과 경로
        String resultPath = "/profiles";
        File resultFIle = new File(absolutePath, userId + resultPath);
        if (!new File(absolutePath, userId + resultPath).exists()) {
            System.out.println("폴더 생성");
            if(!new File(absolutePath, userId + resultPath).mkdir()) {
                System.out.println("생성 실패!");
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
