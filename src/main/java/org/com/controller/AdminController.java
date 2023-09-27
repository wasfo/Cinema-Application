package org.com.controller;


import lombok.extern.slf4j.Slf4j;
import org.com.dto.CinemaDto;
import org.com.dto.MovieDto;
import org.com.dto.TicketStatisticDto;
import org.com.entity.*;
import org.com.exceptions.CinemaCreationException;
import org.com.exceptions.CinemaException;
import org.com.exceptions.MovieDuplicateException;
import org.com.exceptions.SeatException;
import org.com.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Time;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
@RequestMapping("/admin")
public class AdminController {

    private final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private AdminServices adminServices;


    @GetMapping
    public String admin() {
        return "admin/admin_manager";
    }

    @PostMapping("/seats/reserve")
    public String reserveSeat(
            @RequestParam("cinemaId") Long cinemaId,
            @RequestParam("seatNumber") String seatNumber,
            @RequestParam("username") String username)
            throws SeatException, CinemaException {


        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        if (userEmail.equals("anonymousUser")) {
            return "redirect:/login";
        }

        Cinema cinema = adminServices.getCinemaService().findById(cinemaId);
        Optional<User> user = adminServices.getUserService().findByEmail(username);

        Optional<Seat> seat = adminServices.getSeatsService().reserveSeat(cinema, user.get(),
                Integer.parseInt(seatNumber));

        adminServices.getTicketService().createTicket(cinema, user.get(), seat.get());

        logger.info("{} booked a ticket", userEmail);
        return "redirect:/seats?cinemaId=" + cinemaId + "&success";

    }

    @GetMapping("/cinema/create")
    public String showCinemaForm(Model model) {
        model.addAttribute("cinemaDto", new CinemaDto());

        List<Movie> movieList = adminServices.getMovieService().findAllMovies();

        model.addAttribute("movies", movieList);

        List<Room> roomList = adminServices.getRoomService().findAllRooms();
        model.addAttribute("rooms", roomList);

        return "admin/create_cinema";
    }


    @PostMapping("/cinema/create")
    public String createCinema(@ModelAttribute CinemaDto cinemaDto,
                               @RequestParam("movieId") long movieId,
                               @RequestParam("roomId") long roomId) throws CinemaCreationException {

        Room room = adminServices.getRoomService().findRoomById(roomId);
        Movie movie = adminServices.getMovieService().findById(movieId);
        cinemaDto.setRoom(room);
        cinemaDto.setMovie(movie);
        cinemaDto.setStartTime(cinemaDto.getStartTime() + ":00");
        LocalTime startTime = Time.valueOf(cinemaDto.getStartTime()).toLocalTime();
        LocalTime endTime = startTime.plusMinutes(movie.getDurationInMinutes());
        cinemaDto.setEndTime(endTime.toString() + ":00");

        logger.info("start time is {}", startTime);
        logger.info("end time is {}", endTime);

        adminServices.getCinemaService().createCinema(cinemaDto);
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
        adminServices.getMovieService().saveImage(image);
        adminServices.getMovieService().createMovie(movieDto, ImageName);
        return "redirect:/admin/movie/create?success";
    }

    @PostMapping("/seats/deleteAll")
    public String deleteSeats(@RequestParam("cinemaId") long cinemaId) {
        adminServices.getSeatsService().deleteAllSeats(cinemaId);
        return "redirect:/cinemas?success";
    }

    @PostMapping("/cinema/delete")
    public String deleteCinema(@RequestParam("cinemaId") long cinemaId)
            throws CinemaException {

        adminServices.getCinemaService().deleteCinemaById(cinemaId);
        return "redirect:/cinemas?deleted";
    }

    @GetMapping("/cinema/stats")
    public String getStatistics(Model model) {
        List<TicketStatisticDto> stats = adminServices.getStatisticsService().getMovieTicketStatistics();
        long totalIncome = adminServices.getStatisticsService().getTotalIncome(stats);

        model.addAttribute("stats", stats);
        model.addAttribute("totalIncome", totalIncome);

        return "admin/statistics";
    }

}
