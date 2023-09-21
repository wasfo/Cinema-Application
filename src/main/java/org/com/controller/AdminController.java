package org.com.controller;


import jakarta.validation.Valid;
import org.com.dto.CinemaDto;
import org.com.dto.MovieDto;
import org.com.entity.Movie;
import org.com.entity.Room;
import org.com.service.CinemaService;
import org.com.service.MovieService;
import org.com.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {


    private final CinemaService cinemaService;
    private final MovieService movieService;
    private RoomService roomService;


    @Autowired
    public AdminController(CinemaService cinemaService,
                           MovieService movieService,
                           RoomService roomService) {
        this.cinemaService = cinemaService;
        this.movieService = movieService;
        this.roomService = roomService;
    }

    @GetMapping
    public String admin() {
        System.out.println("HERE!!!!!!!!!!!!!!!!! INSIDE ADMIN");
        return "WELCOME ADMIN !!";
    }

    @GetMapping("/cinema/create")
    public String showCreateCinemaForm(Model model) {
        model.addAttribute("cinemaDto", new CinemaDto());

        List<Movie> movieList = movieService.findAllMovies();

        model.addAttribute("movies", movieList);

        List<Room> roomList = roomService.findAllRooms();
        model.addAttribute("rooms", roomList);

        return "createcinema";
    }

    @PostMapping("/cinema/create")
    public String createCinema(@ModelAttribute CinemaDto cinemaDto,
                               @RequestParam("movieId") long movieId,
                               @RequestParam("roomId") long roomId) {

        Room room = roomService.findRoomById(roomId);
        Movie movie = movieService.findById(movieId);
        cinemaDto.setRoom(room);
        cinemaDto.setMovie(movie);
        cinemaService.createCinema(cinemaDto);
        return "redirect:/cinemas";
    }
    @DeleteMapping("/seats/deleteAll")
    public String deleteSeats(@PathVariable long cinemaId) {

        return "redirect:/cinemas";
    }

    @DeleteMapping("/cinema/delete/{cinemaId}")
    public String deleteCinema(@PathVariable long cinemaId) {

        return "redirect:/cinemas";
    }

}
