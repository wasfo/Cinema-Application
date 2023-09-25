package org.com.controller;


import org.com.dto.SeatDto;
import org.com.entity.Cinema;
import org.com.entity.Seat;
import org.com.entity.User;
import org.com.exceptions.CinemaException;
import org.com.exceptions.SeatException;
import org.com.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class SeatController {

    private final SeatsService seatsService;
    private final CinemaService cinemaService;
    private final UserService userService;
    private final TicketService ticketService;
    private final ValidationService validationService;

    @Autowired
    public SeatController(SeatsService seatsService, CinemaService cinemaService, UserService userService, TicketService ticketService, ValidationService validationService) {
        this.seatsService = seatsService;
        this.cinemaService = cinemaService;
        this.userService = userService;
        this.ticketService = ticketService;
        this.validationService = validationService;
    }


    @GetMapping("/seats")
    public String getAllSeats(@RequestParam(value = "cinemaId") Long cinemaId,
                              Model model) throws CinemaException {


        List<SeatDto> seats = seatsService.findAllSeats(cinemaId);

        model.addAttribute("seats", seats);
        model.addAttribute("cinemaId", cinemaId);
        return "seats";
    }

    @PostMapping("/seats/reserve")
    public String reserveSeat(
            @RequestParam("cinemaId") Long cinemaId, @RequestParam("seatNumber") String seatNumber)
            throws SeatException, CinemaException {


        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        if (userEmail.equals("anonymousUser")) {
            return "redirect:/login";
        }


        Cinema cinema = cinemaService.findById(cinemaId);
        Optional<User> user = userService.findByEmail(userEmail);

        seatsService.reserveSeat(cinema, user.get(), Integer.parseInt(seatNumber));

        Seat seat = seatsService.findBySeatNumber(Integer.parseInt(seatNumber));

        ticketService.createTicket(cinema, user.get(), seat);
        return "redirect:/seats?cinemaId=" + cinemaId + "&success";

    }
}
