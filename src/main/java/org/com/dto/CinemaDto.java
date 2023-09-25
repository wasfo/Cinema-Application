package org.com.dto;


import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.com.entity.Movie;
import org.com.entity.Room;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CinemaDto {
    private Long id;
    private String showtime;
    private Date showdate;
    private short availableseats;
    private float price;
    private Room room;
    private Movie movie;

    @Override
    public String toString() {
        return "CinemaDto{" +
                "id=" + id +
                ", showtime='" + showtime + '\'' +
                ", showdate='" + showdate + '\'' +
                ", availableseats=" + availableseats +
                ", price=" + price +
                ", room=" + room +
                ", movie=" + movie +
                '}';
    }
}
