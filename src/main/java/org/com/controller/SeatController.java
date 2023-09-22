package org.com.controller;


import org.com.service.SeatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/seats")
public class SeatController {

    private final SeatsService seatsService;

    @Autowired
    public SeatController(SeatsService seatsService) {
        this.seatsService = seatsService;
    }


    @DeleteMapping("delete/{seatId}")
    public String deleteSeat(@PathVariable Long seatId) {
        seatsService.deleteSeat(seatId);
        return null;
    }
}
