package com.shashankc7.linkedin.posts_service.service;

import com.shashankc7.linkedin.posts_service.dto.PostCreateRequestDto;
import com.shashankc7.linkedin.posts_service.dto.PostDto;
import com.shashankc7.linkedin.posts_service.entity.Post;
import com.shashankc7.linkedin.posts_service.exception.ResourceNotFoundException;
import com.shashankc7.linkedin.posts_service.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostsService
{
    private final PostsRepository postsRepository;
    private final ModelMapper modelMapper;

    public PostDto createPost(PostCreateRequestDto postCreateRequestDto, Long userId)
    {
        Post post = modelMapper.map(postCreateRequestDto, Post.class);
        post.setUserId(userId);

        Post savedPost = postsRepository.save(post);
        return modelMapper.map(savedPost, PostDto.class);
    }

    public PostDto getPostById(Long postId)
    {
        log.debug("Retreiving the post using postId");
        Post post = postsRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));
        return modelMapper.map(post, PostDto.class);
    }

    public List<PostDto> getAllPostsOfUser(Long userId)
    {
       List<Post> posts =  postsRepository.findAllByUserId(userId);
       return posts.stream().map((x) -> modelMapper.map(x,PostDto.class)).collect(Collectors.toUnmodifiableList());
    }
}
