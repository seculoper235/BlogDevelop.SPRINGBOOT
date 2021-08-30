package com.example.blogdevelop.Web.Post.Service;

import com.example.blogdevelop.Domain.File;
import com.example.blogdevelop.Domain.Post;
import com.example.blogdevelop.Repository.PostRepository;
import com.example.blogdevelop.Util.FileService;
import com.example.blogdevelop.Web.Post.Dto.Mapper.PostMapper;
import com.example.blogdevelop.Web.Post.Dto.PostListResponse;
import com.example.blogdevelop.Web.Post.Dto.PostRequest;
import com.example.blogdevelop.Web.Post.Dto.PostResponse;
import com.example.blogdevelop.Web.Setting.Dto.ImageType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
// TODO 맵퍼 작성
public class PostService {
    private final PostRepository postRepository;
    private final FileService fileService;

    // 전체 소제목 리스트 조회
    public PostListResponse allPosts() {
        List<Post> posts = postRepository.findAll();
        return null;
    }

    // 카테고리별 포스트 조회
    public PostListResponse allPostsByCategory(int catId) {
        List<Post> postList = postRepository.findAllByCategory_Id(catId);
        return null;
    }

    // 포스트 한건 조회
    public PostResponse selectPost(int postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow();
        return null;
    }


    // 포스트 이미지들 업로드
    public List<String> uploadPostFiles(List<MultipartFile> multipartFiles, int postId) throws IOException {
        // 파일 업로드만 실행
        Post post = postRepository.findById(postId).orElseThrow();
        List<String> postPathList = fileService.uploadPosts(ImageType.POST, multipartFiles, post.getUser().getId(), postId);
        return null;
    }

    // 포스트 이미지들 업로드 삭제
    public boolean setDeleteUploadFlag(String filename) {
        return fileService.setDeleteFlag(filename).isDeleteFlag();
    }

    // 포스트 한건 등록
    // 다시 file들을 일일히 조회하여 수정하는 것은 비효율적
    // --> 업로드된 파일들을 가지고 File 데이터를 생성하여 DB에 저장
    public PostResponse publishPost(PostRequest postRequest) {
        // 현재 post에 해당하는 file들 조회하여 post를 설정
        // Post가 등록되지 않은 filePathList를 어떻게 얻을지 생각
        // --> 파일들의 업로드 경로는 클라이언트가 임시 저장하여 PostRequest에 담아 보냄
        List<File> postFileList = null;

        // PostRequest에 파일 리스트 저장
        postRequest.setFileList(postFileList);

        // Post 저장
        Post post = postRepository.save(PostMapper.toEntity(postRequest));

        return PostMapper.toResponse(post);
    }

    // 포스트 한건 삭제
    // 포스트 업로드 폴더 자체와 포스트 데이터 삭제
    public void deletePost(int postId) {
        fileService.deletePostDir(postId);
        postRepository.deleteById(postId);
    }
}
