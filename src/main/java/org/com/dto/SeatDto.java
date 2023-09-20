package org.com.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.com.entity.Cinema;
import org.com.entity.Seat;

@Getter
@Setter
@NoArgsConstructor
public class SeatDto {
    private Seat.seatType seatType;
    private int seatNumber;
    private Cinema cinema;
}
