package org.com.service;


import org.com.dto.SeatDto;
import org.com.entity.Cinema;
import org.com.entity.Seat;
import org.com.entity.User;
import org.com.exceptions.CinemaNotFoundException;
import org.com.exceptions.SeatAlreadyReservedException;
import org.com.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeatsService {

    private final SeatRepository seatRepository;
    private CinemaService cinemaService;

    @Autowired
    public SeatsService(SeatRepository seatRepository, CinemaService cinemaService) {
        this.seatRepository = seatRepository;
        this.cinemaService = cinemaService;
    }

    public List<SeatDto> findAvailableSeats(Long cinemaId) {
        List<Seat> reservedSeats = seatRepository.findByCinemaId(cinemaId);
        List<SeatDto> seatDtos = reservedSeats
                .stream()
                .map(this::mapSeatToDto)
                .toList();

        int[] excluded = new int[64];
        int c = 0;
        for (SeatDto seatDto : seatDtos) {
            seatDto.setReserved(true);
            excluded[c] = seatDto.getSeatNumber(); // 0 1 2 3 4 5          // 5 4 11 33 60
            c++;
        }
        List<SeatDto> availableSeats = new ArrayList<>();
        for (int i = 0; i < 64; i++) {
            if (excluded[i] == 0) {
                SeatDto seatDto = (i <= 8)
                        ? new SeatDto(i, Seat.seatType.PREMIUM)
                        : new SeatDto(i, Seat.seatType.CLASSIC);
                seatDto.setReserved(false);
                availableSeats.add(seatDto);
            }
        }
        return availableSeats;
    }

    public Seat findBySeatNumber(int seatNum) {
        return seatRepository.findBySeatNumber(seatNum);
    }

    public int reserveSeat(Cinema cinema, User user, int seatNumber) throws SeatAlreadyReservedException {

        try {
            Seat seat = new Seat(seatNumber, cinema);
            seatRepository.save(seat);
            return seatNumber;
        } catch (Exception e) {
            throw new SeatAlreadyReservedException("this seat has been already reserved.");
        }

    }

    public SeatDto mapSeatToDto(Seat seat) {
        SeatDto seatDto = new SeatDto();
        seatDto.setSeatNumber(seat.getSeatNumber());
        seatDto.setCinemaId(seat.getCinema().getId());
        seatDto.setSeatType(seat.getSeatType());
        return seatDto;
    }
}
