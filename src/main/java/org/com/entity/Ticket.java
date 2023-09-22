package org.com.entity;

import jakarta.persistence.*;
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

    @Column
    private int seatNumber;

    @Column
    private String roomName;

    @Column
    private String showDate;

    @Column
    private String showTime;

    @Column
    private float price;

    public Ticket(String movieName,
                  String username, int seatNumber,
                  String roomName, String showDate,
                  String showTime, float price) {

        this.movieName = movieName;
        this.username = username;
        this.seatNumber = seatNumber;
        this.roomName = roomName;
        this.showDate = showDate;
        this.showTime = showTime;
        this.price = price;
    }
}