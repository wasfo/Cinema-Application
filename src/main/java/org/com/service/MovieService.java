package org.com.service;

import org.com.dto.MovieDto;
import org.com.entity.Movie;
import org.com.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> findAllMovies() {
        return movieRepository.findAll();
    }

    public List<MovieDto> findAllMoviesToDto() {
        List<Movie> movieList = movieRepository.findAll();
        return movieList
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Movie findById(long movieId) {
        return movieRepository.findById(movieId).get();
    }

    public MovieDto convertToDto(Movie movie) {

        return new MovieDto(
                movie.getId(),
                movie.getName(),
                movie.getDurationInMinutes(),
                movie.getRating()
        );
    }
}
