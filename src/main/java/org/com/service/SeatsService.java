package org.com.service;


import jakarta.transaction.Transactional;
import org.com.dto.SeatDto;
import org.com.entity.Cinema;
import org.com.entity.Seat;
import org.com.entity.User;
import org.com.exceptions.CinemaNotFoundException;
import org.com.exceptions.SeatAlreadyReservedException;
import org.com.exceptions.SeatNotFoundException;
import org.com.repository.SeatRepository;
import org.com.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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

    public List<SeatDto> findAllSeats(Long cinemaId) throws CinemaNotFoundException {
        List<Seat> reservedSeats = seatRepository.findByCinemaId(cinemaId);
        List<Integer> seatNumbers = new ArrayList<>(reservedSeats
                .stream()
                .map(Seat::getSeatNumber)
                .toList());

        seatNumbers.sort(((o1, o2) -> o1 - o2));
        Queue<Integer> queue = new LinkedList<>(seatNumbers);

        Cinema cinema = cinemaService.findById(cinemaId);
        int numberOfSeats = cinema.getRoom().getNumberOfSeats();

        List<SeatDto> allseats = new ArrayList<>();
        for (int i = 1; i <= numberOfSeats; i++) {
            SeatDto seat = new SeatDto(i );
            if (!queue.isEmpty() && queue.peek() == i) {
                seat.setReserved(true);
                queue.poll();
            } else {
                seat.setReserved(false);
            }
            allseats.add(seat);
        }


        return allseats;
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

    public static void main(String[] args) {


    }
}
