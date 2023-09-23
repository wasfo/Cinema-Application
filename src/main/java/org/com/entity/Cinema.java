package org.com.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "cinemas", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"showtime", "showdate", "room_id"})
})
@NoArgsConstructor

public class Cinema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "showtime")
    private String showtime;

    @Column(name = "showdate")
    private String showdate;

    @Column(name = "available_seats")
    private short availableseats;

    @Column(name = "ticket_price")
    private float price;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @OneToMany(mappedBy = "cinema", cascade = CascadeType.ALL)
    private List<Ticket> tickets;


    public Cinema(String showtime, String showdate,
                  short availableseats,
                  float price,
                  Room room,
                  Movie movie) {

        this.showtime = showtime;
        this.showdate = showdate;
        this.availableseats = availableseats;
        this.price = price;
        this.room = room;
        this.movie = movie;
    }

    @Override
    public String toString() {
        return "Cinema{" +
                "showtime='" + showtime + '\'' +
                ", showdate='" + showdate + '\'' +
                ", availableseats=" + availableseats +
                ", price=" + price +
                '}';
    }
}
