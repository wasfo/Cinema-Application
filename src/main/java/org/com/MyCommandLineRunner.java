package org.com;

import jakarta.transaction.Transactional;
import org.com.dto.CinemaDto;
import org.com.entity.*;
import org.com.exceptions.CinemaException;
import org.com.repository.*;
import org.com.service.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cglib.core.Local;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class MyCommandLineRunner implements CommandLineRunner {

    @Autowired
    private CinemaService cinemaService;
    @Autowired
    private CinemaRepository cinemaRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    @Transactional
    public void run(String... args) {
//        createUsers();
//        createCinemas();
    }


    public List<Cinema> findCinemasByDate(Date date) {
        return cinemaRepository.findByShowDate(date);
    }


    public void createUsers() {
        Role admin = new Role("ROLE_ADMIN");
        Role user = new Role("ROLE_USER");
        String password = passwordEncoder.encode("12321");
        roleRepository.saveAll(List.of(admin, user));
        User ahmad = new User("ahmad omar", "ahmad", password, List.of(user));
        User ali = new User("ali omar", "ali", password, List.of(user));
        User sara = new User("sara omari", "sara", password, List.of(user));
        User adminUser = new User("ahmad wasfi",
                "admin",
                password, List.of(admin));

        userRepository.saveAll(List.of(ahmad, ali, sara, adminUser));
    }


    public void createCinemas() {
        Movie avengers = new Movie("avengers", (short) 120, 7.9F, "Action", "Joe Russo");
        Movie shutter = new Movie("shutter Island", (short) 150, 8.5F, "Mystery", "Martin Scorsese");
        Movie frozen = new Movie("Frozen", (short) 180, 8.2F, "Family", "Jennifer Lee");
        Room room = new Room("A", 64);
        Room room2 = new Room("B", 64);
        roomRepository.save(room);
        roomRepository.save(room2);
        movieRepository.save(avengers);
        movieRepository.save(shutter);
        movieRepository.save(frozen);


        Date date1 = Date.valueOf("2023-11-31");

        Cinema cinema1 = new Cinema(
                Time.valueOf(LocalTime.of(3, 0)),
                Time.valueOf(LocalTime.of(3, 0).plusMinutes(avengers.getDurationInMinutes())),
                date1,
                (short) 64,
                15, room, avengers);

        Cinema cinema2 = new Cinema(
                Time.valueOf(LocalTime.of(15, 0)),
                Time.valueOf(LocalTime.of(15, 0).plusMinutes(avengers.getDurationInMinutes())),
                Date.valueOf("2023-09-30"),
                (short) 64,
                15, room2, shutter);
        Cinema cinema3 = new Cinema(
                Time.valueOf(LocalTime.of(18, 0)),
                Time.valueOf(LocalTime.of(18, 0).plusMinutes(avengers.getDurationInMinutes())),
                Date.valueOf("2023-10-05"),
                (short) 64,
                15, room, frozen);
        Cinema cinema4 = new Cinema(
                Time.valueOf(LocalTime.of(12, 0)),
                Time.valueOf(LocalTime.of(12, 0).plusMinutes(avengers.getDurationInMinutes())),
                Date.valueOf("2023-09-12"),
                (short) 64,
                15, room, frozen);
        cinemaRepository.saveAll(List.of(cinema1, cinema2, cinema3, cinema4));
    }


}