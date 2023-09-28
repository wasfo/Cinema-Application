package org.com.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.com.dto.TicketStatisticDto;
import org.com.entity.Stats;
import org.com.entity.Ticket;
import org.com.repository.StatisticsRepository;
import org.com.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class StatisticsService {

    @Autowired
    private StatisticsRepository statisticsRepository;
    @Autowired
    private TicketRepository ticketRepository;

    public long getTodayIncome() {
        List<Ticket> tickets = ticketRepository.findAll();
        if (tickets.isEmpty())
            return 0;
        return calculateTicketsIncome(tickets);
    }

    public List<Stats> findAllStats() {
        return statisticsRepository.findAll();
    }

    public long getAllTimeIncome(List<Stats> stats) {
        long total = 0;
        for (Stats stat : stats) {
            total += stat.getTotalTicketsSold();
        }
        return total;
    }

    private long calculateTicketsIncome(List<Ticket> tickets) {
        long total = 0;
        for (Ticket ticket : tickets) {
            total += ticket.getPrice();
        }
        return total;
    }

}
