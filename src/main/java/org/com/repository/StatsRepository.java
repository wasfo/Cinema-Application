package org.com.repository;


import org.com.entity.Stats;
import org.com.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface StatsRepository extends JpaRepository<Stats, Long> {

    public Stats findStatsByMovieName(String movieName);
}
