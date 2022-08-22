package com.example.serialist.services;

import com.example.serialist.models.Movie;
import com.example.serialist.repositories.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;

    public Iterable<Movie> getMovies() {
        return movieRepository.findAll();
    }


    public Movie getMovieById(Long id) {
        return movieRepository.findById(id).orElse(null);
    }

    public Movie getMovieByTitle(String name) {
       return movieRepository.findByTitle(name);
    }
}
