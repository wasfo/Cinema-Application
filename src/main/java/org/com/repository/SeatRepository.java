package org.com.repository;

import org.com.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {

    public List<Seat> findByCinemaId(long cinemaId);

    public Seat findBySeatNumber(int seatNum);

}
