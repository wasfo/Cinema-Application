package org.com.service;


import org.com.entity.*;
import org.com.exceptions.TicketException;
import org.com.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public List<Ticket> findAllTickets() {
        return ticketRepository.findAll();
    }

    public void createTicket(Cinema cinema, User user, Seat seat) throws TicketException {
        try {
            float ticketPrice = cinema.getPrice() + seat.getSeatPrice();
            Ticket ticket = new Ticket(cinema.getMovie().getName(),
                    user.getEmail(), cinema, ticketPrice);

            saveTicket(ticket);
        } catch (Exception e) {
            throw new TicketException("one or more entities were not found");
        }


    }

    public void saveTicket(Ticket ticket) {
        ticketRepository.save(ticket);
    }
}
