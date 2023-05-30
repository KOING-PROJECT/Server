package com.koing.server.koing_server.service.post;

import com.koing.server.koing_server.common.dto.SuccessResponse;
import com.koing.server.koing_server.common.dto.SuperResponse;
import com.koing.server.koing_server.common.error.ErrorCode;
import com.koing.server.koing_server.common.exception.DBFailException;
import com.koing.server.koing_server.common.exception.IOFailException;
import com.koing.server.koing_server.common.exception.NotAcceptableException;
import com.koing.server.koing_server.common.exception.NotFoundException;
import com.koing.server.koing_server.common.success.SuccessCode;
import com.koing.server.koing_server.domain.image.PostPhoto;
import com.koing.server.koing_server.domain.image.repository.PostPhotoRepository;
import com.koing.server.koing_server.domain.image.repository.PostPhotoRepositoryImpl;
import com.koing.server.koing_server.domain.post.Post;
import com.koing.server.koing_server.domain.post.repository.PostRepository;
import com.koing.server.koing_server.domain.post.repository.PostRepositoryImpl;
import com.koing.server.koing_server.domain.user.User;
import com.koing.server.koing_server.domain.user.repository.UserRepositoryImpl;
import com.koing.server.koing_server.service.post.dto.post.PostCreateDto;
import com.koing.server.koing_server.service.post.dto.post.PostListResponseDto;
import com.koing.server.koing_server.service.post.dto.post.PostResponseDto;
import com.koing.server.koing_server.service.s3.component.AWSS3Component;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final Logger LOGGER = LoggerFactory.getLogger(PostService.class);
    private final PostPhotoRepositoryImpl postPhotoRepositoryImpl;
    private final PostPhotoRepository postPhotoRepository;
    private final AWSS3Component awss3Component;
    private final UserRepositoryImpl userRepositoryImpl;
    private final PostRepository postRepository;
    private final PostRepositoryImpl postRepositoryImpl;

    @Transactional
    public SuperResponse createPost(PostCreateDto postCreateDto, List<MultipartFile> postPhotos) {

        LOGGER.info("[PostService] POST 생성 시도");

        Post post = new Post(postCreateDto);

        Post savedPost = postRepository.save(post);

        if (savedPost == null) {
            throw new DBFailException("게시글 저장 과정에서 오류가 발생했습니다.", ErrorCode.DB_FAIL_CREATE_POST_EXCEPTION);
        }

        uploadPhotos(savedPost, postCreateDto.getPostPhotoOrders(), postCreateDto.getUploadedPostPhotoUrls(), postPhotos);

        User writeUser = getUser(postCreateDto.getWriterId());

        savedPost.setCreateUser(writeUser);

        Post updatedPost = postRepository.save(savedPost);

        if (updatedPost.getCreateUser() == null) {
            throw new DBFailException("게시글 업데이트 과정에서 오류가 발생했습니다.", ErrorCode.DB_FAIL_UPDATE_POST_EXCEPTION);
        }

        LOGGER.info("[PostService] POST 생성 성공");

        return SuccessResponse.success(SuccessCode.POST_CREATE_SUCCESS, null);
    }

    @Transactional
    public SuperResponse getPosts() {
        LOGGER.info("[PostService] POST 조회 시도");

        List<Post> posts = postRepositoryImpl.findAllPosts();

        List<PostResponseDto> postResponseDtos = new ArrayList<>();

        for (Post post : posts) {
            postResponseDtos.add(new PostResponseDto(post));
        }

        LOGGER.info("[PostService] POST 조회 성공");

        return SuccessResponse.success(SuccessCode.GET_POSTS_SUCCESS, new PostListResponseDto(postResponseDtos));
    }



    private void uploadPhotos(Post post, List<String> postPhotoOrders, List<String> uploadedPostPhotoUrls, List<MultipartFile> multipartFiles) {
        LOGGER.info("[PostService] 게시글 사진 s3에 upload 시도");

        if (uploadedPostPhotoUrls != null && uploadedPostPhotoUrls.size() > 0) {
            for (String uploadedPostPhotoUrl : uploadedPostPhotoUrls) {

                PostPhoto postPhoto = getPhoto(uploadedPostPhotoUrl);

                int idx = postPhotoOrders.indexOf(uploadedPostPhotoUrl);
                if (idx < 0) {
                    throw new NotAcceptableException("해당 사진의 순서를 알 수 없습니다.", ErrorCode.NOT_ACCEPTABLE_CAN_NOT_COGNITION_ORDER_EXCEPTION);
                }

                postPhoto.setPostPhotoOrder(idx);
                postPhotoRepository.save(postPhoto);
            }
        }

        if (multipartFiles != null) {
            for (MultipartFile multipartFile : multipartFiles) {
                try {
                    String originName = multipartFile.getOriginalFilename();
                    System.out.println("originName = " + originName);
                    int idx = postPhotoOrders.indexOf(originName);
                    if (idx < 0) {
                        throw new NotAcceptableException("해당 사진의 순서를 알 수 없습니다.", ErrorCode.NOT_ACCEPTABLE_CAN_NOT_COGNITION_ORDER_EXCEPTION);
                    }

                    String filePath = awss3Component.convertAndUploadFiles(multipartFile, "post/postPhotos");

                    PostPhoto postPhoto = createPostPhoto(post, idx, originName, filePath);
                    PostPhoto savedPostPhoto = postPhotoRepository.save(postPhoto);

                    if (savedPostPhoto == null) {
                        throw new DBFailException("게시글 사진 저장 과정에서 오류가 발생했습니다.", ErrorCode.DB_FAIL_CREATE_POST_PHOTO_EXCEPTION);
                    }
                } catch (IOException ioException) {
                    throw new IOFailException("이미지 저장 과정에서 오류가 발생했습니다.", ErrorCode.DB_FAIL_UPLOAD_IMAGE_FAIL_EXCEPTION);
                }
            }
        }

        LOGGER.info("[PostService] 게시글 사진 s3에 upload 완료");
    }

    private PostPhoto getPhoto(String filePath) {
        PostPhoto postPhoto = postPhotoRepositoryImpl.findPostPhotoByFilePath(filePath);

        if (postPhoto == null) {
            throw new NotFoundException("해당 게시글 사진을 찾을 수 없습니다.", ErrorCode.NOT_FOUND_POST_PHOTO_EXCEPTION);
        }

        return postPhoto;
    }

    private PostPhoto createPostPhoto(Post post, int order, String originName, String filePath) {
        PostPhoto postPhoto = new PostPhoto();
        postPhoto.setPostPhotoOrder(order);
        postPhoto.setOriginName(originName);
        postPhoto.setFilePath(filePath);
        postPhoto.setPost(post);

        return postPhoto;
    }

    private User getUser(Long userId) {
        User user = userRepositoryImpl.loadUserByUserId(userId, true);
        if (user == null) {
            throw new NotFoundException("해당 유저를 찾을 수 없습니다.", ErrorCode.NOT_FOUND_USER_EXCEPTION);
        }

        return user;
    }


}
