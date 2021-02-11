package com.fmi.reviews.web.mvc;

import com.fmi.reviews.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MovieMvcController {

    MovieService movieService;

    @Autowired
    public MovieMvcController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping("/movies")
    String getMovies(Model model){
        model.addAttribute("movies", movieService.getMovies());

        return "movies";
    }
}
