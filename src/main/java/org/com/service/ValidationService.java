package org.com.service;


import org.com.entity.Cinema;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

@Service

public class ValidationService {


    public boolean isSeatNumberValid(int seatNumber, int limit) {
        return seatNumber > 0 && seatNumber <= limit;
    }

    public boolean isCinemaExpired(Cinema cinema) {
        Date currentDate = Date.valueOf(LocalDate.now());
        Time currentTime = Time.valueOf(LocalTime.now());
        boolean isDateExpired = cinema.getShowDate().before(currentDate); // True
        if (isDateExpired)
            return true;
        else {
            boolean isTimeExpired = cinema.getStartTime().before(currentTime);
            return isTimeExpired;
        }

    }
}
