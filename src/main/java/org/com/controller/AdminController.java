package org.com.controller;


import lombok.extern.slf4j.Slf4j;
import org.com.dto.CinemaDto;
import org.com.dto.MovieDto;
import org.com.dto.TicketStatisticDto;
import org.com.entity.Movie;
import org.com.entity.Room;
import org.com.exceptions.CinemaCreationException;
import org.com.exceptions.CinemaException;
import org.com.exceptions.MovieDuplicateException;
import org.com.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Time;
import java.time.LocalTime;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/admin")
public class AdminController {

    private final Logger logger = LoggerFactory.getLogger(AdminController.class);
    private final CinemaService cinemaService;
    private final SeatsService seatsService;
    private final MovieService movieService;
    private final StatisticsService statisticsService;
    private final RoomService roomService;


    @Autowired
    public AdminController(CinemaService cinemaService,
                           SeatsService seatsService, MovieService movieService,
                           StatisticsService statisticsService, RoomService roomService) {
        this.cinemaService = cinemaService;
        this.seatsService = seatsService;
        this.movieService = movieService;
        this.statisticsService = statisticsService;
        this.roomService = roomService;
    }

    @GetMapping
    public String admin() {
        return "admin/admin_manager";
    }

    @GetMapping("/cinema/create")
    public String showCinemaForm(Model model) {
        model.addAttribute("cinemaDto", new CinemaDto());

        List<Movie> movieList = movieService.findAllMovies();

        model.addAttribute("movies", movieList);

        List<Room> roomList = roomService.findAllRooms();
        model.addAttribute("rooms", roomList);

        return "admin/create_cinema";
    }

    @PostMapping("/cinema/create")
    public String createCinema(@ModelAttribute CinemaDto cinemaDto,
                               @RequestParam("movieId") long movieId,
                               @RequestParam("roomId") long roomId) throws CinemaCreationException {

        Room room = roomService.findRoomById(roomId);
        Movie movie = movieService.findById(movieId);
        cinemaDto.setRoom(room);
        cinemaDto.setMovie(movie);
        cinemaDto.setStartTime(cinemaDto.getStartTime() + ":00");
        LocalTime startTime = Time.valueOf(cinemaDto.getStartTime()).toLocalTime();
        LocalTime endTime = startTime.plusMinutes(movie.getDurationInMinutes());
        cinemaDto.setEndTime(endTime.toString() + ":00");

        logger.info("start time is {}", startTime);
        logger.info("end time is {}", endTime);

        cinemaService.createCinema(cinemaDto);
        return "redirect:/cinemas";
    }

    @GetMapping("/movie/create")
    public String showCreateMovieForm(Model model) {
        model.addAttribute("movieDto", new MovieDto());
        return "admin/create_movie";
    }

    @PostMapping(value = "/movie/create")
    public String createMovie(@ModelAttribute MovieDto movieDto,
                              @RequestParam("file") MultipartFile image) throws MovieDuplicateException {
        String ImageName = image.getOriginalFilename();
        movieService.saveImage(image);
        movieService.createMovie(movieDto, ImageName);
        return "redirect:/admin/movie/create?success";
    }

    @PostMapping("/seats/deleteAll")
    public String deleteSeats(@RequestParam("cinemaId") long cinemaId) {
        seatsService.deleteAllSeats(cinemaId);
        return "redirect:/cinemas?success";
    }

    @PostMapping("/cinema/delete")
    public String deleteCinema(@RequestParam("cinemaId") long cinemaId)
            throws CinemaException {

        cinemaService.deleteCinemaById(cinemaId);
        return "redirect:/cinemas?deleted";
    }

    @GetMapping("/cinema/stats")
    public String getStatistics(Model model) {
        List<TicketStatisticDto> stats = statisticsService.getMovieTicketStatistics();
        long totalIncome = statisticsService.getTotalIncome(stats);

        model.addAttribute("stats", stats);
        model.addAttribute("totalIncome", totalIncome);

        return "admin/statistics";
    }

}
