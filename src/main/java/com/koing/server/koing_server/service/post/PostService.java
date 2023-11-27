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
import com.koing.server.koing_server.service.post.dto.post.PostLikeRequestDto;
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
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
    public SuperResponse getPosts(Long userId) {
        LOGGER.info("[PostService] Post 조회 시도");

        List<Post> posts = postRepositoryImpl.findAllPosts();

        User loginUser = getUser(userId);

        List<PostResponseDto> postResponseDtos = new ArrayList<>();

        for (Post post : posts) {
            postResponseDtos.add(new PostResponseDto(post, loginUser));
        }

        LOGGER.info("[PostService] Post 조회 성공");

        if (loginUser.getUserOptionalInfo() != null) {
            if (loginUser.getUserOptionalInfo().getImageUrls() != null && loginUser.getUserOptionalInfo().getImageUrls().size() > 0) {

                return SuccessResponse.success(
                        SuccessCode.GET_POSTS_SUCCESS,
                        new PostListResponseDto(
                                loginUser.getUserOptionalInfo().getImageUrls().get(0),
                                postResponseDtos
                        )
                );
            }
        }

        return SuccessResponse.success(SuccessCode.GET_POSTS_SUCCESS, new PostListResponseDto(postResponseDtos));
    }

    @Transactional
    public SuperResponse getAdminPosts(Long userId) {
        LOGGER.info("[PostService] Admin Post 조회 시도");

        List<Post> posts = postRepositoryImpl.findAllPosts();

        User loginUser = getUser(userId);

        List<PostResponseDto> postResponseDtos = new ArrayList<>();

        for (Post post : posts) {
            postResponseDtos.add(new PostResponseDto(post, loginUser));
        }

        LOGGER.info("[PostService] Admin Post 조회 성공");

        if (loginUser.getUserOptionalInfo() != null) {
            if (loginUser.getUserOptionalInfo().getImageUrls() != null && loginUser.getUserOptionalInfo().getImageUrls().size() > 0) {

                return SuccessResponse.success(
                        SuccessCode.GET_ADMIN_POSTS_SUCCESS,
                        new PostListResponseDto(
                                loginUser.getUserOptionalInfo().getImageUrls().get(0),
                                postResponseDtos
                        )
                );
            }
        }

        return SuccessResponse.success(SuccessCode.GET_ADMIN_POSTS_SUCCESS, new PostListResponseDto(postResponseDtos));
    }

    @Transactional
    public SuperResponse pressLikePost(PostLikeRequestDto postLikeRequestDto) {

        LOGGER.info("[PostService] Post 좋아요 수정 시도");

        Post post = getPost(postLikeRequestDto.getPostId());

        User user = getUser(postLikeRequestDto.getUserId());

        if (post.getLikedUsers().contains(user)) {
            post.deleteLikedUser(user);
        }
        else {
            post.addLikedUser(user);
        }

        Post savedPost = postRepository.save(post);

        LOGGER.info("[PostService] Post 좋아요 수정 성공");

        return SuccessResponse.success(SuccessCode.POST_PRESS_LIKE_SUCCESS, null);
    }

    @Transactional
    public SuperResponse getMyPosts(Long userId) {

        LOGGER.info("[PostService] 생성한 post 리스트 조회 시도");

        List<Post> createPosts = postRepositoryImpl.findPostByUserId(userId);

        User loginUser = getUser(userId);

        List<PostResponseDto> postResponseDtos = new ArrayList<>();

        for (Post post : createPosts) {
            postResponseDtos.add(new PostResponseDto(post, loginUser));
        }

        LOGGER.info("[PostService] 생성한 post 리스트 조회 성공");

        return SuccessResponse.success(SuccessCode.GET_CREATE_POSTS_SUCCESS, new PostListResponseDto(postResponseDtos));
    }

    @Transactional
    public SuperResponse getLikePosts(Long userId) {
        LOGGER.info("[PostService] 좋아요 누른 post 리스트 조회 시도");

        User user = userRepositoryImpl.findLikePostByUserLikedPost(userId);

        List<Post> likedPost = user.getLikePosts().stream().sorted((p1, p2) -> p1.getCreatedAt().compareTo(p2.getCreatedAt())).collect(Collectors.toList());

        List<PostResponseDto> postResponseDtos = new ArrayList<>();

        for (Post post : likedPost) {
            postResponseDtos.add(new PostResponseDto(post, user));
        }

        LOGGER.info("[PostService] 좋아요 누른 post 리스트 조회 성공");

        return SuccessResponse.success(SuccessCode.GET_LIKE_POSTS_SUCCESS, new PostListResponseDto(postResponseDtos));
    }

    public SuperResponse deletePost(final Long userId, final Long postId) {
        LOGGER.info("[PostService] post 삭제 시도");
        final Post post = postRepositoryImpl.findPostByPostId(postId);

        if (post == null) {
            throw new NotFoundException("해당 게시글을 찾을 수 없습니다.", ErrorCode.NOT_FOUND_POST_EXCEPTION);
        }

        if (post.getCreateUser().getId() != userId) {
            throw new NotAcceptableException("게시글을 작성한 유저가 아닙니다.", ErrorCode.NOT_ACCEPTABLE_NOT_POSTED_USER_EXCEPTION);
        }

        post.delete();
        LOGGER.info("[PostService] post 삭제 성공");

        return SuccessResponse.success(SuccessCode.DELETE_POST_SUCCESS, null);
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

    private Post getPost(Long postId) {
        Post post = postRepositoryImpl.findPostByPostId(postId);

        if (post == null) {
            throw new NotFoundException("해당 게시글을 찾을 수 없습니다.", ErrorCode.NOT_FOUND_POST_EXCEPTION);
        }

        return post;
    }
}
