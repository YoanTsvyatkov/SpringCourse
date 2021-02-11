package course.spring.restmvc.impl;

import course.spring.restmvc.dao.PostRepository;
import course.spring.restmvc.exception.NonexistingEntityException;
import course.spring.restmvc.model.Post;
import course.spring.restmvc.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public Post getPostById(String id) {
        return postRepository.findById(id).orElseThrow(() -> new NonexistingEntityException(String.format("Post with ID: %s does not exist.", id)));
    }

    @Override
    public Post addPost(Post post) {
        post.setId(null);
        return postRepository.insert(post);
    }

    @Override
    public Post updatePost(Post post) {
        getPostById(post.getId());
        post.setModified(LocalDateTime.now());
        return postRepository.save(post);
    }

    @Override
    public Post deletePost(String id) {
        Post postById = getPostById(id);
        postRepository.deleteById(id);
        return postById;
    }

    @Override
    public long getCount() {
        return postRepository.count();
    }
}
