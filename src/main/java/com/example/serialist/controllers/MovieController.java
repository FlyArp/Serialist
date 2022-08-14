package com.example.serialist.controllers;

import com.example.serialist.models.Movie;
import com.example.serialist.services.MovieService;
import com.example.serialist.services.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;
    private final UserService userService;

    @GetMapping("/")
    public String movies(Model model) {
        model.addAttribute("movies", movieService.getMovies());
        return "index";
    }

    @GetMapping("/movie/image/{id}")
    public void showMovieImage(@PathVariable Long id, HttpServletResponse response) throws IOException {
        response.setContentType("image/jpg");
        Movie movie = movieService.getMovieById(id);
        InputStream inputStream = new ByteArrayInputStream(movie.getImage());
        IOUtils.copy(inputStream, response.getOutputStream());
    }

    @GetMapping("/movie/{id}")
    public String movie(@PathVariable Long id, Model model) {
        model.addAttribute("movie", movieService.getMovieById(id));
        return "movie-page";
    }
}
