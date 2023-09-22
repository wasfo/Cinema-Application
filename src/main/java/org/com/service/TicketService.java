package org.com.service;


import org.com.entity.*;
import org.com.exceptions.TicketException;
import org.com.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TicketService {

    private TicketRepository ticketRepository;
    private CinemaRepository cinemaRepository;
    private UserRepository userRepository;
    private SeatRepository seatRepository;
    private RoomRepository roomRepository;

    @Autowired
    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public void createTicket(Cinema cinema, User user, Seat seat) {

        Ticket ticket = new Ticket(cinema, user, seat, cinema.getRoom());
        saveTicket(ticket);

        throw new TicketException("one or more entities were not found");


    }

    public void saveTicket(Ticket ticket) {
        ticketRepository.save(ticket);
    }
}
