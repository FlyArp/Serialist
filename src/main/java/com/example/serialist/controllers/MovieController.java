package com.example.serialist.controllers;

import com.example.serialist.models.Movie;
import com.example.serialist.models.User;
import com.example.serialist.services.MovieService;
import com.example.serialist.services.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.Set;

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
    public String movie(@PathVariable Long id, Model model, Principal principal) {
        User user = userService.getUserByPrincipal(principal);
        Movie movie = movieService.getMovieById(id);
        Set<Movie> movies = user.getMovieList();
        boolean hasTheMovie;
        if (movies == null) {
            hasTheMovie = false;
        } else {
            hasTheMovie = movies.contains(movie);
        }
        model.addAttribute("movie", movie);
        model.addAttribute("hasTheMovie", hasTheMovie);
        return "movie-page";
    }

    @PostMapping("/movie/{id}")
    public String addRemoveMovie(@PathVariable Long id, Principal principal, @RequestParam String action) {
        if (isAuthenticated()) {
            User user = userService.getUserByPrincipal(principal);
            Movie movie = movieService.getMovieById(id);
            if (action.equals("add")) {
                userService.addToWatchLater(user, movie);
            } else {
                userService.removeFromWatchLater(user, movie);
            }
            return "redirect:/movie/{id}";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/search")
    public String searchMovie(@RequestParam String title, Model model) {
        Movie movie = movieService.getMovieByTitle(title);
        boolean isMovieFound = true;
        if (movie == null) {
            isMovieFound = false;
        }
        model.addAttribute("isMovieFound", isMovieFound);
        model.addAttribute("movie", movie);
        return "search-result";
    }

    private boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || AnonymousAuthenticationToken.class.
                isAssignableFrom(authentication.getClass())) {
            return false;
        }
        return authentication.isAuthenticated();
    }
}
