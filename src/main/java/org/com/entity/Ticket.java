package org.com.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users_tickets")
@Setter
@Getter
@NoArgsConstructor
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String movieName;

    @Column
    private String username;

    @ManyToOne
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "seat_id")
    private Seat seat;

    @Column
    private float price;

    public Ticket(String movieName,
                  String username,
                  Cinema cinema,
                  Seat seat,
                  float price) {

        this.movieName = movieName;
        this.username = username;
        this.cinema = cinema;
        this.seat = seat;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", movieName='" + movieName + '\'' +
                ", username='" + username + '\'' +
                ", cinema=" + cinema +
                ", price=" + price +
                '}';
    }
}