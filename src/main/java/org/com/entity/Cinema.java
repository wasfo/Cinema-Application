package org.com.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Entity
@Table(name = "cinemas")
public class Cinema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "showtime")
    private LocalTime showtime;

    @Column(name = "showdate")
    private LocalDate showdate;

    @Column(name = "available_seats")
    private short availableseats;

    @Column(name = "ticket_price")
    private float price;

    @OneToMany(mappedBy = "cinema")
    private List<Ticket> tickets;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;
}
