package org.com.config;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.com.entity.Cinema;
import org.com.entity.Stats;
import org.com.entity.Ticket;
import org.com.exceptions.CinemaException;
import org.com.repository.CinemaRepository;
import org.com.repository.StatisticsRepository;
import org.com.repository.TicketRepository;
import org.com.service.CinemaService;
import org.com.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Configuration
@EnableScheduling
@Slf4j
public class Scheduler {

    @Autowired
    private CinemaService cinemaService;
    @Autowired
    private TicketService ticketService;
    @Autowired
    private StatisticsRepository statisticsRepository;

    @Scheduled(fixedRate = 60 * 60 * 24 * 1000)
    @Transactional
    public void harvestLoot() throws CinemaException {

        List<Cinema> expiredCinemas = cinemaService.findExpiredCinemas();
        log.info("expired cinemas {}", expiredCinemas);

        if (expiredCinemas.isEmpty())
            return;

        long totalDailySum = 0;
        long numOfTickets = 0;
        for (Cinema cinema : expiredCinemas) {
            List<Ticket> tickets = ticketService.findByCinemaId(cinema.getId());
            double sum = tickets.stream().mapToDouble(Ticket::getPrice).sum();
            numOfTickets += tickets.size();
            totalDailySum += sum;
        }
        log.info("Total Daily Income {}", totalDailySum);

        saveStats(totalDailySum, numOfTickets);

        log.info("Daily stats saved in database");

        deleteExpiredCinemas(expiredCinemas);
    }

    private void saveStats(long totalDailySum, long numOfTickets) {
        Stats stats = new Stats();
        stats.setDate(Date.valueOf(LocalDate.now()));
        stats.setTotalTicketsSold(totalDailySum);
        stats.setTicketsCount(numOfTickets);
        statisticsRepository.save(stats);
    }


    private void deleteExpiredCinemas(List<Cinema> expiredCinemas) throws CinemaException {
        for (Cinema cinema : expiredCinemas) {
            ticketService.deleteTicketsByCinemaId(cinema.getId());
            cinemaService.deleteCinemaById(cinema.getId());
        }
    }
}
