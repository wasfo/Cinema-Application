package org.com.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static org.com.entity.Seat.SeatType.CLASSIC;
import static org.com.entity.Seat.SeatType.PREMIUM;

@Entity
@Getter
@Setter
@Table(name = "reserved_seats", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"number", "cinema_id"})
}
)
@NoArgsConstructor
public class Seat {
    public enum SeatType {
        CLASSIC,
        PREMIUM
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private SeatType seatType;

    @Column(name = "number")
    private int seatNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    public Seat(int seatNumber, Cinema cinema, User user) {
        this.seatNumber = seatNumber;
        if (seatNumber <= 8)
            this.seatType = PREMIUM;
        else this.seatType = CLASSIC;
        this.cinema = cinema;
        this.user = user;
    }

    public int getSeatPrice() {
        if (seatType == PREMIUM)
            return 20;
        else
            return 10;
    }

}
