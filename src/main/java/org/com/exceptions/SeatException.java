package org.com.exceptions;


import lombok.Getter;

@Getter
public class SeatException extends Exception {

    private long cinemaId;

    public SeatException(String message, long cinemaId) {
        super(message);
        this.cinemaId = cinemaId;
    }
}
