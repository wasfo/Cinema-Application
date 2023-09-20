package org.com.service;


import org.com.dto.CinemaDto;
import org.com.dto.UserDto;
import org.com.entity.Cinema;
import org.com.entity.User;
import org.com.repository.CinemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CinemaService {

    private CinemaRepository cinemaRepository;

    @Autowired
    public CinemaService(CinemaRepository cinemaRepository) {
        this.cinemaRepository = cinemaRepository;
    }

    public List<CinemaDto> findAllCinemas() {
        List<Cinema> cinemas = cinemaRepository.findAll();
        return cinemas.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

    }

    private CinemaDto convertToDto(Cinema cinema) {
        CinemaDto cinemaDto = new CinemaDto();

        cinemaDto.setPrice(cinema.getPrice());
        cinemaDto.setMovie(cinema.getMovie());
        cinemaDto.setAvailableseats(cinema.getAvailableseats());
        cinemaDto.setRoom(cinema.getRoom());
        cinemaDto.setShowtime(cinema.getShowtime());
        cinemaDto.setShowdate(cinema.getShowdate());
        return cinemaDto;
    }

    public boolean createCinema(Cinema cinema) {
        try {
            cinemaRepository.save(cinema);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}
