package org.com.service;


import lombok.Getter;
import org.springframework.stereotype.Service;

@Service
@Getter
public class AdminServices {

    private final CinemaService cinemaService;
    private final SeatsService seatsService;
    private final MovieService movieService;
    private final StatisticsService statisticsService;
    private final RoomService roomService;
    private final UserService userService;
    private final TicketService ticketService;

    public AdminServices(CinemaService cinemaService,
                         SeatsService seatsService,
                         MovieService movieService,
                         StatisticsService statisticsService,
                         RoomService roomService,
                         UserService userService,
                         TicketService ticketService) {
        this.cinemaService = cinemaService;
        this.seatsService = seatsService;
        this.movieService = movieService;
        this.statisticsService = statisticsService;
        this.roomService = roomService;
        this.userService = userService;
        this.ticketService = ticketService;
    }
}
