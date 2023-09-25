package org.com.service;


import org.com.dto.CinemaDto;
import org.com.entity.Cinema;
import org.com.exceptions.CinemaException;
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
        List<Cinema> cinemas = cinemaRepository.findCurrentCinemas();
        return cinemas.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

    }

    public Cinema findById(Long cinemaId) throws CinemaException {
        Cinema cinema = cinemaRepository
                .findById(cinemaId).orElseThrow(() -> new CinemaException("Cinema not found"));
        return cinema;
    }

    public void deleteCinemaById(long cinemaId) throws CinemaException {
        try {
            cinemaRepository.deleteById(cinemaId);
        } catch (Exception e) {
            throw new CinemaException("Cinema still has seats reserved. delete seats first");
        }

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
