package org.com.service;
import jakarta.transaction.Transactional;
import org.com.dto.SeatDto;
import org.com.entity.Cinema;
import org.com.entity.Seat;
import org.com.entity.User;
import org.com.exceptions.CinemaException;
import org.com.exceptions.SeatException;
import org.com.exceptions.SeatNotFoundException;
import org.com.repository.SeatRepository;
import org.com.repository.TicketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class SeatsService {

    private final SeatRepository seatRepository;
    private final TicketRepository ticketRepository;
    private final ValidationService validationService;
    private final CinemaService cinemaService;
    private final Logger logger = LoggerFactory.getLogger(SeatsService.class);

    @Autowired
    public SeatsService(SeatRepository seatRepository,
                        ValidationService validationService, TicketRepository ticketRepository,
                        TicketRepository ticketRepository1, CinemaService cinemaService) {

        this.seatRepository = seatRepository;
        this.validationService = validationService;
        this.ticketRepository = ticketRepository1;
        this.cinemaService = cinemaService;
    }

    public List<SeatDto> findAllSeats(Long cinemaId) throws CinemaException {
        List<Seat> reservedSeats = seatRepository.findByCinemaId(cinemaId);
        List<Integer> seatNumbers = new ArrayList<>(reservedSeats
                .stream()
                .map(Seat::getSeatNumber)
                .toList());

        seatNumbers.sort(((o1, o2) -> o1 - o2));
        Queue<Integer> queue = new LinkedList<>(seatNumbers);
        Cinema cinema = cinemaService.findById(cinemaId);
        int numberOfSeats = cinema.getRoom().getNumberOfSeats();

        List<SeatDto> allSeats = new ArrayList<>();
        for (int i = 1; i <= numberOfSeats; i++) {
            SeatDto seat = new SeatDto(i);
            if (!queue.isEmpty() && queue.peek() == i) {
                seat.setReserved(true);
                queue.poll();
            } else {
                seat.setReserved(false);
            }
            allSeats.add(seat);
        }
        return allSeats;
    }

    @Transactional
    public void deleteAllSeats(long cinemaId) {
        ticketRepository.deleteByCinemaId(cinemaId);
    }

    public Seat findBySeatNumber(int seatNum) {
        return seatRepository.findBySeatNumber(seatNum);
    }

    public Optional<Seat> reserveSeat(Cinema cinema, User user, int seatNumber) throws SeatException, CinemaException {
        boolean isSeatNumberValid = validationService.
                isSeatNumberValid(seatNumber, cinema.getRoom().getNumberOfSeats());

        boolean isCinemaExpired = validationService.isCinemaExpired(cinema);

        logger.info("is cinema {} expired {}", cinema.getId(), isCinemaExpired);

        if (isCinemaExpired) {
            throw new CinemaException("Selected cinema is expired");
        }

        if (!isSeatNumberValid)
            throw new SeatException("Selected is seat out of range", cinema.getId());

        try {
            Seat seat = new Seat(seatNumber, cinema, user);
            seatRepository.save(seat);
            return Optional.of(seat);
        } catch (Exception e) {
            throw new SeatException("This seat is already reserved." + "\n" + "find different one.", cinema.getId());
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
