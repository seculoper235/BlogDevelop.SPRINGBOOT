package com.example.blogdevelop.Web.Post.Service;

import com.example.blogdevelop.Domain.File;
import com.example.blogdevelop.Domain.Post;
import com.example.blogdevelop.Repository.PostRepository;
import com.example.blogdevelop.Util.FileService;
import com.example.blogdevelop.Web.Post.Dto.Mapper.PostMapper;
import com.example.blogdevelop.Web.Post.Dto.PostRequest;
import com.example.blogdevelop.Web.Setting.Dto.ImageType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final FileService fileService;

    // 전체 소제목 리스트 조회
    public void allPosts() {
        List<Post> posts = postRepository.findAll();
    }

    // 포스트 한건 조회
    public void selectPost(int postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow();
    }


    // 포스트 이미지들 업로드
    public void uploadPostFiles(List<MultipartFile> multipartFiles, int postId) throws IOException {
        Post post = postRepository.findById(postId).orElseThrow();
        List<String> postPathList = fileService.uploadPosts(ImageType.POST, multipartFiles, post.getUser().getId(), postId);
    }

    // 포스트 이미지들 업로드 삭제
    public boolean setDeleteUploadFlag(String filename) {
        return fileService.setDeleteFlag(filename).isDeleteFlag();
    }

    // 포스트 한건 등록
    // TODO 다시 file들을 일일히 조회하여 수정하는 것은 비효율적
    public void publishPost(PostRequest postRequest) {
        // Post 저장
        Post post = postRepository.save(PostMapper.toEntity(postRequest));

        // 현재 post에 해당하는 file들 조회하여 post를 설정
        // TODO Post가 등록되지 않은 filePathList를 어떻게 얻을지 생각
        List<File> postFileList = fileService.setPost(null, post);
    }

    // 포스트 한건 삭제
    // 포스트 업로드 폴더 자체와 포스트 데이터 삭제
    public void deletePost(int postId) {
        fileService.deletePostDir(postId);
        postRepository.deleteById(postId);
    }
}
