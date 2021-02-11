package course.spring.myblogsapp.service;

import course.spring.myblogsapp.dao.PostsRepository;
import course.spring.myblogsapp.entities.Post;
import course.spring.myblogsapp.exception.NonExistingEntityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {

    @Autowired
    private PostsRepository postsRepository;

    @Override
    public List<Post> getAllPosts() {
        return postsRepository.findAll();
    }

    @Override
    public Post getPostById(Long id) {
        return postsRepository.findById(id).orElseThrow(() ->
                new NonExistingEntityException("Invalid post id"));
    }

    @Override
    @Transactional
    public Post addPost(@Valid Post post) {
        post.setId(null);
        return postsRepository.save(post);
        ;
    }

    @Override
    public Post updatePost(Post post) {
        getPostById(post.getId());
        return postsRepository.save(post);
    }

    @Override
    public Post deletePost(Long id) {
        Post deleted = getPostById(id);
        postsRepository.deleteById(id);
        return deleted;
    }

    @Override
    public long getPostsCount() {
        return postsRepository.count();
    }
}
