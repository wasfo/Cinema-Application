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

    @GetMapping("/cinemas/{id}/seats")
    public String getAllSeats(@PathVariable Long id, Model model) {
        List<SeatDto> seats = seatsService.findAvailableSeats(id);
        model.addAttribute("seats", seats);
        return "seats";
    }

    @PostMapping("/cinemas/{cinemaId}/seats/reserve")
    public String reserveSeat(@PathVariable Long cinemaId, @RequestParam int seatNumber)
            throws SeatAlreadyReservedException, CinemaNotFoundException {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("USER EMAIL ->" + userEmail);
        Cinema cinema = cinemaService.findById(cinemaId);
        Optional<User> user = userService.findByEmail(userEmail);
        System.out.println("cinema  ->" + cinema);
        System.out.println("found user ->" + user.get());
        seatsService.reserveSeat(cinema, user.get(), seatNumber);

        Seat seat = seatsService.findBySeatNumber(seatNumber);

        ticketService.createTicket(cinema, user.get(), seat);
        return "redirect:/cinemas/" + cinemaId + "/seats?Success";

    }
}
