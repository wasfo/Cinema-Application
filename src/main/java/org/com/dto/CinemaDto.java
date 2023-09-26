package org.com.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.com.entity.Movie;
import org.com.entity.Room;
import org.springframework.cglib.core.Local;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CinemaDto {
    private Long id;
    private String startTime;
    private String endTime;
    private Date showDate;
    private short availableSeats;
    private float price;
    private boolean isExpired;
    private Room room;
    private Movie movie;

    @Override
    public String toString() {
        return "CinemaDto{" +
                "id=" + id +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", showdate=" + showDate +
                ", availableseats=" + availableSeats +
                ", price=" + price +
                ", isExpired=" + isExpired +
                ", room=" + room +
                ", movie=" + movie +
                '}';
    }

}
