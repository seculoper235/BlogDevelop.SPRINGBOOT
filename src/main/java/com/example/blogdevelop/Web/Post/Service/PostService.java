package com.example.blogdevelop.Web.Post.Service;

import com.example.blogdevelop.Repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    // 전체 소제목 리스트 조회

    // 포스트 한건 조회
    // TODO 이미지 파일 경로도 같이 불러와야 함

    // 포스트 이미지들 업로드

    // 포스트 이미지들 업로드 삭제
    // TODO 업로드 취소 버튼을 누르면, deleteFlag를 1로 바꾼 뒤 Post 업데이트 할 때 한꺼번에 삭제할 건지,
    //                           아니면 fileService 에서 삭제 메소드를 구현하여 그것을 사용할 건지 결정!

    // 포스트 한건 등록
    // TODO 이미지에는 파일 경로 주소가 들어감

    // 포스트 한건 삭제
    // TODO 포스트 업로드 폴더 자체와 포스트 데이터 삭제
}
