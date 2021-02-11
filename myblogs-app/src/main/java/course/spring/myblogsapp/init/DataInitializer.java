package course.spring.myblogsapp.init;

import course.spring.myblogsapp.entities.Post;
import course.spring.myblogsapp.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {
    @Autowired
    PostService postService;

    private static final List<Post> SAMPLE_POSTS = List.of(
            new Post("New in Spring 5", "WebFlux is here ...",
                    "https://www.publicdomainpictures.net/pictures/40000/velka/spring-flowers-13635086725Z1.jpg",
                    List.of("spring", "new")),
            new Post("Reactive Programming in Spring", "Project Reactor is employed ...",
                    "https://www.publicdomainpictures.net/pictures/40000/velka/spring-flowers-13635086725Z1.jpg",
                    List.of("spring", "reactive")),
            new Post("Autowiring and DI", "Autowiring is a flexible way to inject components ...",
                    "https://www.publicdomainpictures.net/pictures/40000/velka/spring-flowers-13635086725Z1.jpg",
                    List.of("spring", "autowiring"))
    );

    @Override
    public void run(String... args) throws Exception {

    }
}
