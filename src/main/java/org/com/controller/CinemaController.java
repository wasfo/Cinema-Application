package org.com.controller;


import org.com.dto.CinemaDto;
import org.com.repository.CinemaRepository;
import org.com.service.CinemaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CinemaController {

    private final Logger logger = LoggerFactory.getLogger(CinemaController.class);
    private final CinemaService cinemaService;

    @Autowired
    public CinemaController(CinemaService cinemaService, CinemaRepository repo) {
        this.cinemaService = cinemaService;

    }

    @GetMapping("/cinemas")
    public String listAllCinemas(Model model) {

        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info("logged in user is {}", userEmail);

        List<CinemaDto> cinemas = cinemaService.findAllCinemas();
        model.addAttribute("cinemas", cinemas);
        return "cinemas";
    }

}
