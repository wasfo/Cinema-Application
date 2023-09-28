package org.com;

import lombok.extern.slf4j.Slf4j;
import org.com.entity.Cinema;
import org.com.entity.Ticket;
import org.com.repository.CinemaRepository;
import org.com.repository.TicketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.List;


@SpringBootApplication

public class CinemaApp {


    public static void main(String[] args) {
        SpringApplication.run(CinemaApp.class, args);
    }


}
