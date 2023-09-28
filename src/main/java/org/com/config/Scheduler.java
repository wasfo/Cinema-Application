package org.com.config;
import lombok.extern.slf4j.Slf4j;
import org.com.entity.Cinema;
import org.com.entity.Ticket;
import org.com.repository.CinemaRepository;
import org.com.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@Configuration
@EnableScheduling
@Slf4j
public class Scheduler {

    @Autowired
    private CinemaRepository cinemaRepository;
    @Autowired
    private TicketRepository ticketRepository;

    @Scheduled(fixedRate = 30 * 1000)
    public void cleanupExpiredCinemas() {
        List<Cinema> expiredCinemas = cinemaRepository.findPreviousCinemas();
        log.info("number of expired cinemas {}", expiredCinemas.size());
        long totalDailySum = 0;
        for (Cinema cinema : expiredCinemas) {
            List<Ticket> tickets = ticketRepository.findByCinemaId(cinema.getId());
            double sum = tickets.stream().mapToDouble(Ticket::getPrice).sum();
            totalDailySum += sum;
        }
        log.info("total Daily Sum {}", totalDailySum);
    }
}
