package org.com.controller;


import org.com.dto.CinemaDto;
import org.com.dto.SeatDto;
import org.com.dto.UserDto;
import org.com.service.CinemaService;
import org.com.service.SeatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
public class CinemaController {

    private final CinemaService cinemaService;
    private final SeatsService seatsService;

    @Autowired
    public CinemaController(CinemaService cinemaService,
                            SeatsService seatsService) {
        this.cinemaService = cinemaService;
        this.seatsService = seatsService;
    }

    @GetMapping("/cinemas")
    public String listAllCinemas(Model model) {
        System.out.println("----------------------------------------------------");
        System.out.println("name -> " + SecurityContextHolder.getContext().getAuthentication().getName());
        System.out.println("role -> " + SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        System.out.println("----------------------------------------------------");
        List<CinemaDto> cinemas = cinemaService.findAllCinemas();
        model.addAttribute("cinemas", cinemas);
        return "cinemas";
    }

    @GetMapping("/cinemas/{id}/seats")
    public String getAllSeats(@PathVariable Long id, Model model) {
        List<SeatDto> seats = seatsService.findAvailableSeats(id);
        System.out.println(seats);
        model.addAttribute("seats", seats);
        return "seats";
    }
}
