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

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private ValidationService validationService;


    public List<Ticket> findTicketsByUsername(String username) {
        return ticketRepository.findTicketByUsername(username);
    }

    public void deleteTicketById(long ticketId) {
        Optional<Ticket> ticket = ticketRepository.findById(ticketId);
        if (ticket.isPresent()) {
            Cinema cinema = ticket.get().getCinema();
            boolean isCinemaExpired = validationService.isCinemaExpired(cinema);
            if (!isCinemaExpired)
                ticketRepository.deleteById(ticketId);
            else
                throw new TicketException("This ticket can't be refunded, because the date is expired");
        }

    }

    public Optional<Ticket> findById(long id) {
        return ticketRepository.findById(id);
    }


    public void createTicket(Cinema cinema, User user, Seat seat) throws TicketException {
        try {
            float ticketPrice = cinema.getPrice() + seat.getSeatPrice();
            Ticket ticket = new Ticket(cinema.getMovie().getName(),
                    user.getEmail(), cinema, seat, ticketPrice);

            ticketRepository.save(ticket);
        } catch (Exception e) {
            throw new TicketException("one or more entities were not found");
        }
    }


}
