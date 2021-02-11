package course.spring.restmvc.service;

import course.spring.restmvc.model.Post;

import java.util.List;

public interface PostService {
    List<Post> getAllPosts();

    Post getPostById(String id);

    Post addPost(Post post);

    Post updatePost(Post post);

    Post deletePost(String id);

    long getCount();
}
