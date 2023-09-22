package org.com.service;


import org.com.dto.CinemaDto;
import org.com.dto.UserDto;
import org.com.entity.Cinema;
import org.com.entity.User;
import org.com.exceptions.CinemaNotFoundException;
import org.com.repository.CinemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.AccessMode;
import java.util.List;
import java.util.Optional;
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

    public Cinema findById(Long cinemaId) throws CinemaNotFoundException {
        Cinema cinema = cinemaRepository
                .findById(cinemaId).orElseThrow(() -> new CinemaNotFoundException("Cinema not found"));
        return cinema;
    }

    private CinemaDto convertToDto(Cinema cinema) {
        CinemaDto cinemaDto = new CinemaDto();
        cinemaDto.setId(cinema.getId());
        cinemaDto.setPrice(cinema.getPrice());
        cinemaDto.setMovie(cinema.getMovie());
        cinemaDto.setAvailableseats(cinema.getAvailableseats());
        cinemaDto.setRoom(cinema.getRoom());
        cinemaDto.setShowtime(cinema.getShowtime());
        cinemaDto.setShowdate(cinema.getShowdate());
        return cinemaDto;
    }

    public void createCinema(CinemaDto cinemaDto) {
        Cinema cinema = new Cinema(
                cinemaDto.getShowtime(),
                cinemaDto.getShowdate(),
                cinemaDto.getAvailableseats(),
                cinemaDto.getPrice(),
                cinemaDto.getRoom(),
                cinemaDto.getMovie());

        cinemaRepository.save(cinema);
    }
}
