package org.com.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.com.dto.TicketStatisticDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class StatisticsService {


    @PersistenceContext
    private EntityManager entityManager;


    public List<TicketStatisticDto> getMovieTicketStatistics() {
        String jpql = "SELECT t.movieName AS movieName, SUM(t.price) AS totalTicketPrice " +
                "FROM Ticket t " +
                "GROUP BY t.movieName";
        TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);

        List<Object[]> results = query.getResultList();

        List<TicketStatisticDto> ticketStatistics = new ArrayList<>();
        for (Object[] result : results) {
            String movieName = (String) result[0];
            Double totalTicketPrice = (Double) result[1];


            TicketStatisticDto statistic = new TicketStatisticDto();
            statistic.setMovieName(movieName);
            statistic.setTotalTicketsSold(totalTicketPrice);

            ticketStatistics.add(statistic);
        }

        return ticketStatistics;
    }

    public long getTotalIncome(List<TicketStatisticDto> stats) {
        long total = 0;
        for (TicketStatisticDto stat : stats) {
            total += stat.getTotalTicketsSold();
        }
        return total;
    }

}
