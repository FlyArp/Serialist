package com.example.serialist.controllers;

import com.example.serialist.models.Movie;
import com.example.serialist.models.User;
import com.example.serialist.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String signUp(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/signup")
    public String createUser(User user, Model model) {
        if (!userService.createUser(user)) {
            model.addAttribute("errorMessage", "Пользователь с email: " + user.getEmail() + " уже существует");
            return "signup";
        }
        return "redirect:/login";
    }

    @GetMapping("/user")
    public String getUserPage(Model model, Principal principal) {
        User user = userService.getUserByPrincipal(principal);
        List<Movie> movies = user.getMovies();
        model.addAttribute("user", user);
        model.addAttribute("movies", movies);
        return "user-page";
    }
}
