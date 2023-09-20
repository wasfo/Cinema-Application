package org.com.controller;


import org.com.dto.CinemaDto;
import org.com.dto.SeatDto;
import org.com.dto.UserDto;
import org.com.service.CinemaService;
import org.com.service.SeatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class CinemaController {

    private CinemaService cinemaService;
    private SeatsService seatsService;

    @Autowired
    public CinemaController(CinemaService cinemaService,
                            SeatsService seatsService) {
        this.cinemaService = cinemaService;
        this.seatsService = seatsService;
    }

    @GetMapping("/cinemas")
    public String listAllCinemas(Model model) {
        List<CinemaDto> cinemas = cinemaService.findAllCinemas();
        model.addAttribute("cinemas", cinemas);
        return "cinemas";
    }

    @GetMapping("/cinemas/{id}/seats")
    public String getAvailableSeatsForCinema(@PathVariable Long id, Model model) {
        List<SeatDto> seatDtos = seatsService.findReservedSeats(id);
        return "cinemas";
    }
}
