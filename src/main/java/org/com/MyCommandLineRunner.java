package org.com;

import org.com.entity.Movie;
import org.com.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MyCommandLineRunner implements CommandLineRunner {

    private CinemaRepository cinemaRepository;
    private MovieRepository movieRepository;
    private RoomRepository roomRepository;
    private SeatRepository seatRepository;
    private UserRepository userRepository;
    private TicketRepository ticketRepository;

    @Autowired
    public MyCommandLineRunner(CinemaRepository repository,
                               MovieRepository movieRepository,
                               RoomRepository roomRepository,
                               SeatRepository seatRepository,
                               UserRepository userRepository, TicketRepository ticketRepository) {
        this.cinemaRepository = repository;
        this.movieRepository = movieRepository;
        this.roomRepository = roomRepository;
        this.seatRepository = seatRepository;
        this.userRepository = userRepository;
        this.ticketRepository = ticketRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        Movie shatterd = new Movie("shutter Island", (short) 200, 7.9F);
        movieRepository.save(shatterd);

    }
}