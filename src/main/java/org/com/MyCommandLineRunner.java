package org.com;

import jakarta.transaction.Transactional;
import org.com.entity.*;
import org.com.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class MyCommandLineRunner implements CommandLineRunner {

    private CinemaRepository cinemaRepository;
    private MovieRepository movieRepository;
    private RoomRepository roomRepository;
    private SeatRepository seatRepository;
    private UserRepository userRepository;
    private TicketRepository ticketRepository;
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public MyCommandLineRunner(CinemaRepository repository,
                               MovieRepository movieRepository,
                               RoomRepository roomRepository,
                               SeatRepository seatRepository,
                               UserRepository userRepository,
                               TicketRepository ticketRepository,
                               RoleRepository roleRepository) {
        this.cinemaRepository = repository;
        this.movieRepository = movieRepository;
        this.roomRepository = roomRepository;
        this.seatRepository = seatRepository;
        this.userRepository = userRepository;
        this.ticketRepository = ticketRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
    }

    public void createUsers() {
        Role admin = new Role("ROLE_ADMIN");
        Role user = new Role("ROLE_USER");
        String password = "12321";
        roleRepository.saveAll(List.of(admin, user));
        User ahmad = new User("ahmad omar", "ahmad", "12321", List.of(user));
        User ali = new User("ali omar", "ali", "12321", List.of(user));
        User sara = new User("sara omari", "sara", "12321", List.of(user));
        User adminUser = new User("ahmad wasfi",
                "admin",
                passwordEncoder.encode(password), List.of(admin));

        userRepository.saveAll(List.of(ahmad, ali, sara, adminUser));
    }


    public void createCinemas() {
        Movie avengers = new Movie("avengers", (short) 120, 7.9F);
        Movie shutter = new Movie("shutter Island", (short) 150, 8.5F);
        Movie Frozen = new Movie("Frozen", (short) 180, 8.2F);
        Room room = new Room("A", 64);
        roomRepository.save(room);
        movieRepository.save(avengers);
        movieRepository.save(shutter);
        movieRepository.save(Frozen);

        Cinema cinema1 = new Cinema(LocalTime.of(3, 0).toString(),
                LocalDate.of(2023, 10, 1).toString(),
                (short) 64,
                15, room, avengers);

        Cinema cinema2 = new Cinema(LocalTime.of(3, 0).toString(),
                LocalDate.of(2023, 10, 2).toString(),
                (short) 64,
                15, room, shutter);
        Cinema cinema3 = new Cinema(LocalTime.of(2, 0).toString(),
                LocalDate.of(2023, 10, 3).toString(),
                (short) 64,
                15, room, Frozen);
        cinemaRepository.saveAll(List.of(cinema1, cinema2, cinema3));
    }
}