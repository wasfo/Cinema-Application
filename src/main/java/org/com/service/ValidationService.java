package org.com.service;


import lombok.extern.slf4j.Slf4j;
import org.com.dto.CinemaDto;
import org.com.entity.Cinema;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class ValidationService {

    public boolean isCinemaDateBeforeCurrentDate(CinemaDto cinemaToCheck) {
        boolean isBefore = cinemaToCheck.getShowDate().before(Date.valueOf(LocalDate.now()));
        if (isBefore)
            return true;

        if (cinemaToCheck.getShowDate().equals(Date.valueOf(LocalDate.now()))) {
            Time cinemaToCheckStartTime = Time.valueOf(cinemaToCheck.getStartTime());
            return cinemaToCheckStartTime.before(Time.valueOf(LocalTime.now()));
        }
        return false;
    }

    public boolean isCinemaTimeValid(CinemaDto cinemaToCheck, List<CinemaDto> cinemasInSameDate) {
        log.info("cinema to check {}", cinemaToCheck);
        Time cinemaToCheckStartTime = Time.valueOf(cinemaToCheck.getStartTime());
        Time cinemaToCheckEndTime = Time.valueOf(cinemaToCheck.getEndTime());

        log.info("cinemas in same date: {}", cinemasInSameDate);
        if (cinemasInSameDate.isEmpty())
            return true;

        for (CinemaDto cinema : cinemasInSameDate) {
            if (Objects.equals(cinema.getRoom().getName(), cinemaToCheck.getRoom().getName())) {
                log.info("CinemaToCheck Room {}", cinema.getRoom().getName());
                log.info("Cinema Room {}", cinemaToCheck.getRoom().getName());
                Time startTime = Time.valueOf(cinema.getStartTime());
                Time endTime = Time.valueOf(cinema.getEndTime());
                if (timesOverlap(cinemaToCheckStartTime, cinemaToCheckEndTime, startTime, endTime))
                    return false;
            }
        }

        return true;
    }


    public boolean timesOverlap(Time startTime1,
                                Time endTime1,
                                Time startTime2,
                                Time endTime2) {
        log.info("startTime1 {}, endTime1 {}", startTime1, endTime1);
        log.info("startTime2 {}, endTime2 {}", startTime2, endTime2);

        log.info("(endTime1.before(startTime2) {}", endTime1.before(startTime2));
        log.info("(startTime1.after(endTime2) {}", startTime1.after(endTime2));
        if (endTime1.before(startTime2)) {
            return false;
        }
        if (startTime1.after(endTime2)) {
            return false;
        }
        return true;
    }

    public boolean isSeatNumberValid(int seatNumber, int limit) {
        return seatNumber > 0 && seatNumber <= limit;
    }

    public boolean isCinemaExpired(Cinema cinema) {
        Date currentDate = Date.valueOf(LocalDate.now());
        Date cinemaDate = cinema.getShowDate();
        Time currentTime = Time.valueOf(LocalTime.now());
        boolean isBeforeCurrentDate = cinemaDate.before(currentDate);
        boolean isSameAsCurrentDate = cinemaDate.equals(currentDate);
        if (isBeforeCurrentDate)
            return true;
        if (isSameAsCurrentDate) {
            return cinema.getStartTime().before(currentTime);
        }
        return false;
    }


}
