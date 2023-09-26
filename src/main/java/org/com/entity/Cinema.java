package org.com.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "cinemas")
@NoArgsConstructor
public class Cinema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "start_time")
    private Time startTime;

    @Column(name = "end_time")
    private Time endTime;

    @Column(name = "show_date")
    private Date showDate;

    @Column(name = "available_seats")
    private short availableSeats;

    @Column(name = "ticket_price")
    private float price;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @OneToMany(mappedBy = "cinema")
    private List<Ticket> tickets;


    public Cinema(Time startTime,
                  Time endTime,
                  Date showDate,
                  short availableSeats,
                  float price,
                  Room room,
                  Movie movie) {

        this.startTime = startTime;
        this.endTime = endTime;
        this.showDate = showDate;
        this.availableSeats = availableSeats;
        this.price = price;
        this.room = room;
        this.movie = movie;
    }

    @Override
    public String toString() {
        return "Cinema{" +
                "id=" + id +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", showdate=" + showDate +
                ", availableSeats=" + availableSeats +
                ", price=" + price +
                ", room=" + room +
                ", movie=" + movie +
                ", tickets=" + tickets +
                '}';
    }


    public static void main(String[] args) {
        for (int i = 0; i < 24; i++) {

        }
    }
}
