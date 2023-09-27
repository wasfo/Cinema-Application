package org.com.controller;


import org.com.dto.SeatDto;
import org.com.entity.Cinema;
import org.com.entity.Seat;
import org.com.entity.User;
import org.com.exceptions.CinemaException;
import org.com.exceptions.SeatException;
import org.com.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class SeatController {

    private final Logger logger = LoggerFactory.getLogger(SeatController.class);
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

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        List<SeatDto> seats = seatsService.findAllSeats(cinemaId);

        model.addAttribute("seats", seats);
        model.addAttribute("cinemaId", cinemaId);
        return "seats";
    }

    @PostMapping("/seats/reserve")
    public String reserveSeat(
            @RequestParam("cinemaId") Long cinemaId,
            @RequestParam("seatNumber") String seatNumber,
            @RequestParam(name = "username", required = false) String username,
            RedirectAttributes redirectAttributes)
            throws SeatException, CinemaException {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        if (userEmail.equals("anonymousUser")) {
            return "redirect:/login";
        }
        boolean isAdmin = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(role -> role.equals("ROLE_ADMIN"));


        Optional<User> user;
        if (isAdmin) {
            user = userService.findByEmail(username);
        } else {
            user = userService.findByEmail(userEmail);
        }
        if (user.isPresent()) {
            Cinema cinema = cinemaService.findById(cinemaId);
            Optional<Seat> seat = seatsService.reserveSeat(cinema, user.get(), Integer.parseInt(seatNumber));
            ticketService.createTicket(cinema, user.get(), seat.get());
            logger.info("{} booked a ticket", userEmail);
        }

        return "redirect:/seats?cinemaId=" + cinemaId + "&success";

    }
}
