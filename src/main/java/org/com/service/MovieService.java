package org.com.service;

import org.com.dto.MovieDto;
import org.com.entity.Movie;
import org.com.exceptions.MovieDuplicateException;
import org.com.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class MovieService {

    private MovieRepository movieRepository;
    @Value("${images.upload.directory}")
    private String uploadDirectory;

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

    public boolean createMovie(MovieDto movieDto, String imageName) throws MovieDuplicateException {

        Movie movie = new Movie(movieDto.getName(),
                (short) movieDto.getDurationInMinutes(),
                movieDto.getRating());

        movie.setImageName(imageName);

        try {
            movieRepository.save(movie);

            return true;
        } catch (Exception e) {
            throw new MovieDuplicateException("such movie already exists");
        }
    }

    public void saveImage(MultipartFile image) {
        File saveDirectory = new File(uploadDirectory);
        System.out.println(saveDirectory.toURI());
        if (!saveDirectory.exists()) {
            System.out.println("is directory made? TRUE");
            saveDirectory.mkdirs();
        }
        System.out.println("is directory made? FALSE");

        try {
            String originalFileName = image.getOriginalFilename();
            Path filePath = Paths.get(uploadDirectory, originalFileName);
            try (FileOutputStream fos = new FileOutputStream(filePath.toFile())) {
                fos.write(image.getBytes());
                fos.close();
            }
            System.out.println("image file path ->" + filePath.toUri());
            System.out.println("image SIZE " + image.getSize());

        } catch (Exception e) {
            throw new RuntimeException("SOMETHING WENT WRONG UPLOADING THE IMAGE" + "CAUSE:" + e.getCause());
        }
    }

    public MovieDto convertToDto(Movie movie) {
        MovieDto movieDto = new MovieDto();
        movieDto.setId(movie.getId());
        movieDto.setName(movie.getName());
        movieDto.setRating(movie.getRating());
        movieDto.setDurationInMinutes(movie.getDurationInMinutes());
        return new MovieDto();
    }
}
