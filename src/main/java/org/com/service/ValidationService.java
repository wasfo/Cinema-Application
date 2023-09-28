package org.com.service;


import org.com.controller.CinemaController;
import org.com.dto.CinemaDto;
import org.com.entity.Cinema;
import org.com.repository.CinemaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ValidationService {
    private final Logger logger = LoggerFactory.getLogger(ValidationService.class);


    public boolean isCinemaDateBeforeCurrentDate(CinemaDto cinemaToCheck) {
        boolean isBefore = cinemaToCheck.getShowDate().before(Date.valueOf(LocalDate.now()));
        if (isBefore)
            return true;

        boolean isAfter = cinemaToCheck.getShowDate().after(Date.valueOf(LocalDate.now()));
        if (isAfter)
            return false;

        if (cinemaToCheck.getShowDate().equals(Date.valueOf(LocalDate.now()))) {
            Time cinemaToCheckStartTime = Time.valueOf(cinemaToCheck.getStartTime());
            return cinemaToCheckStartTime.before(Time.valueOf(LocalTime.now()));
        }
        return false;
    }

    public boolean isCinemaTimeValid(CinemaDto cinemaToCheck, List<CinemaDto> cinemasInSameDate) {
        logger.info("cinema to check {}", cinemaToCheck);
        Time cinemaToCheckStartTime = Time.valueOf(cinemaToCheck.getStartTime());
        Time cinemaToCheckEndTime = Time.valueOf(cinemaToCheck.getEndTime());

        if (cinemasInSameDate.isEmpty())
            return true;


        for (CinemaDto cinema : cinemasInSameDate) {
            Time startTime = Time.valueOf(cinema.getStartTime());
            Time endTime = Time.valueOf(cinema.getEndTime());
            if (areTimesOverlapping(cinemaToCheckStartTime, cinemaToCheckEndTime, startTime, endTime))
                return false;
        }

        return true;
    }

    public boolean areTimesOverlapping(Time startTime1,
                                       Time endTime1,
                                       Time startTime2,
                                       Time endTime2) {
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
