package org.com.controller;


import org.com.dto.CinemaDto;
import org.com.service.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CinemaController {

    private final CinemaService cinemaService;

    @Autowired
    public CinemaController(CinemaService cinemaService) {
        this.cinemaService = cinemaService;

    }

    @GetMapping("/cinemas")
    public String listAllCinemas(Model model) {
        List<CinemaDto> cinemas = cinemaService.findAllCinemas();
        model.addAttribute("cinemas", cinemas);
        return "cinemas";
    }

}
