package org.com.entity;

import jakarta.persistence.*;
import lombok.Data;
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

    @ManyToOne
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "seat_id")
    private Seat seat;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    public Ticket(Cinema cinema, User user, Seat seat, Room room) {
        this.cinema = cinema;
        this.user = user;
        this.seat = seat;
        this.room = room;
    }
}