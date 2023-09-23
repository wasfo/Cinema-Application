package org.com.exceptions;

public class CinemaStillHasReservedSeatsException extends RuntimeException{

    public CinemaStillHasReservedSeatsException(String message) {
        super(message);
    }
}
