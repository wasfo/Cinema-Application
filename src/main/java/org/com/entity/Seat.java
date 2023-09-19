package org.com.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "reserved_seats")
public class Seat {
    private enum seatType {
        CLASSIC,
        PREMIUM
    }

    private static class Location {
        int x;
        int y;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private seatType seatType;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "x", column = @Column(name = "location_x")),
            @AttributeOverride(name = "y", column = @Column(name = "location_y"))
    })
    private Location location;

    @ManyToOne
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;
}
