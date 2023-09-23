package org.com.service;


import jakarta.transaction.Transactional;
import org.com.dto.SeatDto;
import org.com.entity.Cinema;
import org.com.entity.Seat;
import org.com.entity.User;
import org.com.exceptions.SeatAlreadyReservedException;
import org.com.exceptions.SeatNotFoundException;
import org.com.repository.SeatRepository;
import org.com.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SeatsService {

    private final SeatRepository seatRepository;
    private final TicketRepository ticketRepository;
    private CinemaService cinemaService;

    @Autowired
    public SeatsService(SeatRepository seatRepository,
                        TicketRepository ticketRepository,
                        CinemaService cinemaService) {

        this.seatRepository = seatRepository;
        this.ticketRepository = ticketRepository;

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
                        ? new SeatDto(i, Seat.SeatType.PREMIUM)
                        : new SeatDto(i, Seat.SeatType.CLASSIC);
                seatDto.setReserved(false);
                availableSeats.add(seatDto);
            }
        }
        return availableSeats;
    }

    @Transactional
    public void deleteAllSeats(long cinemaId) {
        seatRepository.deleteByCinemaId(cinemaId);
    }

    public Seat findBySeatNumber(int seatNum) {
        return seatRepository.findBySeatNumber(seatNum);
    }

    public int reserveSeat(Cinema cinema, User user, int seatNumber) throws SeatAlreadyReservedException {

        try {
            Seat seat = new Seat(seatNumber, cinema, user);
            seatRepository.save(seat);
            return seatNumber;
        } catch (Exception e) {
            throw new SeatAlreadyReservedException("this seat has been already reserved.");
        }

    }

    public void deleteSeat(long seatId) throws SeatNotFoundException {
        Optional<Seat> seat = seatRepository.findById(seatId);
        if (seat.isPresent()) {
            seatRepository.deleteById(seat.get().getId());
        } else {
            throw new SeatNotFoundException("Seat with ID " + seatId + " not found");
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
