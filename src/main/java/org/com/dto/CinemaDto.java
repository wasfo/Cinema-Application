package org.com.dto;


import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.com.entity.Movie;
import org.com.entity.Room;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CinemaDto {
    private Long id;
    private String showtime;
    private String showdate;
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
