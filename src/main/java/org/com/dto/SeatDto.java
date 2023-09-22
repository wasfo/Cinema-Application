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
    private Seat.SeatType seatType;
    private int seatNumber;
    private boolean isReserved;
    private long cinemaId;

    public SeatDto(int seatNumber, Seat.SeatType seatType) {
        this.seatType = seatType;
        this.seatNumber = seatNumber;
    }
}
