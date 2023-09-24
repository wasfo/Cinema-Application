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

    public SeatDto(int seatNumber) {
        this.seatNumber = seatNumber;
        if (seatNumber <= 8)
            this.seatType = Seat.SeatType.PREMIUM;
        else this.seatType = Seat.SeatType.CLASSIC;

    }

    @Override
    public String toString() {
        return "SeatDto{" +
                "seatType=" + seatType +
                ", seatNumber=" + seatNumber +
                ", isReserved=" + isReserved +
                '}';
    }
}
