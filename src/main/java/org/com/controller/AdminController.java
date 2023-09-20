package org.com.controller;


import jakarta.validation.Valid;
import org.com.dto.CinemaDto;
import org.com.entity.Movie;
import org.com.entity.Room;
import org.com.service.CinemaService;
import org.com.service.MovieService;
import org.com.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
        System.out.println("HERE!!!!!!!!!!!!!!!!! INSIDE CREATE CINEMA");
        model.addAttribute("cinemaDto", new CinemaDto());

        List<Movie> movieList = movieService.findAllMovies();
        model.addAttribute("movies", movieList);

        List<Room> roomList = roomService.findAllRooms();
        model.addAttribute("rooms", roomList);

        System.out.println(roomList);
        return "createcinema";
    }

    @PostMapping("/cinema/create")
    public String createCinema(@ModelAttribute CinemaDto cinemaDto) {

        System.out.println(cinemaDto);
        return "redirect:/cinemas";
    }

}
