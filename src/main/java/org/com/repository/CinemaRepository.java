package org.com.repository;

import org.com.entity.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CinemaRepository extends JpaRepository<Cinema, Long> {

    @Query("SELECT c FROM Cinema c WHERE c.showdate > CURRENT_TIMESTAMP")
    List<Cinema> findCurrentCinemas();
}
