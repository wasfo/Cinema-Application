package org.com.repository;

import org.com.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {


    public List<Ticket> findTicketByUsername(String username);

    public void deleteByCinemaId(long cinemaId);
}
