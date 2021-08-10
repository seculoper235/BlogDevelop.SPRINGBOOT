package com.example.blogdevelop.Web.Post.Service;

import com.example.blogdevelop.Domain.Post;
import com.example.blogdevelop.Repository.PostRepository;
import com.example.blogdevelop.Util.FileService;
import com.example.blogdevelop.Web.Post.Dto.PostRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    public void uploadPostFiles(List<MultipartFile> multipartFiles, int postId) {
        Post post = postRepository.findById(postId).orElseThrow();
        String postPath = fileService.postFilePath(post.getUser().getId(), postId);
    }

    // 포스트 이미지들 업로드 삭제
    public boolean setDeleteUploadFlag(String filename) {
        return fileService.setDeleteFlag(filename).isDeleteFlag();
    }

    // 포스트 한건 등록
    public void publishPost(PostRequest postRequest) {
        // TODO postRequest를 post로 바꾸고 저장
        Post post = new Post();
    }

    // 포스트 한건 삭제
    // 포스트 업로드 폴더 자체와 포스트 데이터 삭제
    public void deletePost(int postId) {
        fileService.deletePostDir(postId);
        postRepository.deleteById(postId);
    }
}
