package org.com.repository;


import org.com.entity.Stats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.sql.Date;

@Repository

public interface StatisticsRepository extends JpaRepository<Stats, Long> {


    public Stats findByDate(Date date);
}
