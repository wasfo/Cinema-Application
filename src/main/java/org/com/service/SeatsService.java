package org.com.service;


import org.com.dto.SeatDto;
import org.com.entity.Seat;
import org.com.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeatsService {

    private SeatRepository seatRepository;

    @Autowired
    public SeatsService(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    public List<SeatDto> findReservedSeats(Long cinemaId) {
        List<Seat> seats = seatRepository.findByCinemaId(cinemaId);
        return seats
                .stream()
                .map(this::mapSeatToDto)
                .collect(Collectors.toList());

    }

    public SeatDto mapSeatToDto(Seat seat) {
        SeatDto seatDto = new SeatDto();
        seatDto.setSeatNumber(seat.getSeatNumber());
        seatDto.setCinema(seat.getCinema());
        seatDto.setSeatType(seat.getSeatType());
        return seatDto;
    }
}
