package org.com.controller;


import org.com.dto.CinemaDto;
import org.com.dto.SeatDto;
import org.com.entity.Cinema;
import org.com.entity.Seat;
import org.com.entity.User;
import org.com.exceptions.CinemaNotFoundException;
import org.com.exceptions.SeatAlreadyReservedException;
import org.com.service.CinemaService;
import org.com.service.SeatsService;
import org.com.service.TicketService;
import org.com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class CinemaController {

    private final CinemaService cinemaService;
    private final SeatsService seatsService;
    private final TicketService ticketService;
    private final UserService userService;

    @Autowired
    public CinemaController(CinemaService cinemaService,
                            SeatsService seatsService, TicketService ticketService, UserService userService) {
        this.cinemaService = cinemaService;
        this.seatsService = seatsService;
        this.ticketService = ticketService;
        this.userService = userService;
    }

    @GetMapping("/cinemas")
    public String listAllCinemas(Model model) {
        List<CinemaDto> cinemas = cinemaService.findAllCinemas();
        model.addAttribute("cinemas", cinemas);
        return "cinemas";
    }

    @GetMapping("/cinemas/seats")
    public String getAllSeats(@RequestParam(value = "cinemaId") Long cinemaId, Model model) {


        List<SeatDto> seats = seatsService.findAvailableSeats(cinemaId);

        model.addAttribute("seats", seats);
        model.addAttribute("cinemaId", cinemaId);
        return "seats";
    }


    @PostMapping("/cinemas/seats/reserve")
    public String reserveSeat(
            @RequestParam("cinemaId") Long cinemaId, @RequestParam("seatNumber") String seatNumber)
            throws SeatAlreadyReservedException, CinemaNotFoundException {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        if (userEmail.equals("anonymousUser")) {
            return "redirect:/login";
        }

        Cinema cinema = cinemaService.findById(cinemaId);
        Optional<User> user = userService.findByEmail(userEmail);


        seatsService.reserveSeat(cinema, user.get(), Integer.parseInt(seatNumber));

        Seat seat = seatsService.findBySeatNumber(Integer.parseInt(seatNumber));

        ticketService.createTicket(cinema, user.get(), seat);
        return "redirect:/cinemas/seats?cinemaId=" + cinemaId + "&Success";

    }
}
