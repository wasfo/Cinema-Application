package org.com.controller;


import org.com.entity.Ticket;
import org.com.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    public String showTickets(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Ticket> tickets = ticketService.findTicketsByUsername(username);
        model.addAttribute("tickets", tickets);
        return "tickets";
    }

    @PostMapping("/delete")
    public String deleteTicket(@RequestParam("ticketId") long ticketId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        ticketService.deleteTicketById(ticketId);
        return "redirect:/tickets?success";
    }
}
