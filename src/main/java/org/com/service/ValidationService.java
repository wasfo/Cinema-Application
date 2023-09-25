package org.com.service;


import org.springframework.stereotype.Service;

@Service

public class ValidationService {


    public boolean isSeatNumberValid(int seatNumber, int limit) {
        return seatNumber > 0 && seatNumber <= limit;
    }
}
