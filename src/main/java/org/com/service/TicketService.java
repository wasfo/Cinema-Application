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


    @Autowired
    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public List<Ticket> findTicketsByUsername(String username) {
        return ticketRepository.findTicketByUsername(username);
    }

    public void deleteTicketById(long ticketId) {
        ticketRepository.deleteById(ticketId);
    }

    public Optional<Ticket> findById(long id) {
        return ticketRepository.findById(id);
    }


    public void createTicket(Cinema cinema, User user, Seat seat) throws TicketException {
        try {
            float ticketPrice = cinema.getPrice() + seat.getSeatPrice();
            Ticket ticket = new Ticket(cinema.getMovie().getName(),
                    user.getEmail(), cinema, seat, ticketPrice);

            saveTicket(ticket);
        } catch (Exception e) {
            throw new TicketException("one or more entities were not found");
        }


    }

    public void saveTicket(Ticket ticket) {
        ticketRepository.save(ticket);
    }
}
