package course.spring.myblogsapp.dao;

import course.spring.myblogsapp.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostsRepository extends JpaRepository<Post, Long> {

}
