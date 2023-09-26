package org.com.service;


import org.com.dto.CinemaDto;
import org.com.entity.Cinema;
import org.com.exceptions.CinemaCreationException;
import org.com.exceptions.CinemaException;
import org.com.repository.CinemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CinemaService {

    private final CinemaRepository cinemaRepository;
    private final ValidationService validationService;

    @Autowired
    public CinemaService(CinemaRepository cinemaRepository,
                         ValidationService validationService) {
        this.cinemaRepository = cinemaRepository;
        this.validationService = validationService;
    }

    public List<CinemaDto> findNonExpiredCinemas() {
        List<Cinema> cinemas = cinemaRepository.findCurrentCinemas();
        return cinemas.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

    }

    public List<CinemaDto> findByDate(Date date) {
        List<Cinema> cinemas = cinemaRepository.findByShowDate(date);

        return cinemas.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<CinemaDto> findAllCinemas() {
        List<Cinema> cinemas = cinemaRepository.findAll();
        return cinemas.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

    }

    public Cinema findById(Long cinemaId) throws CinemaException {
        return cinemaRepository
                .findById(cinemaId)
                .orElseThrow(() -> new CinemaException("Cinema not found"));
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
        cinemaDto.setAvailableSeats(cinema.getAvailableSeats());
        cinemaDto.setRoom(cinema.getRoom());
        cinemaDto.setStartTime(cinema.getStartTime().toString());
        cinemaDto.setEndTime(cinema.getEndTime().toString());
        cinemaDto.setShowDate(cinema.getShowDate());
        cinemaDto.setExpired(validationService.isCinemaExpired(cinema));
        return cinemaDto;
    }

    public void createCinema(CinemaDto cinemaDto) throws CinemaCreationException {

        List<CinemaDto> cinemas = findByDate(cinemaDto.getShowDate());

        boolean isCinemaDateBeforeCurrentDate = validationService.isCinemaDateBeforeCurrentDate(cinemaDto);
        if (isCinemaDateBeforeCurrentDate)
            throw new CinemaCreationException("cinema time is before current date");

        boolean isValid = validationService.isCinemaTimeValid(cinemaDto, cinemas);
        if (!isValid)
            throw new CinemaCreationException("cinema overlaps with other cinemas. check cinema list first.");
        Cinema cinema = new Cinema(
                Time.valueOf(cinemaDto.getStartTime()),
                Time.valueOf(cinemaDto.getEndTime()),
                cinemaDto.getShowDate(),
                cinemaDto.getAvailableSeats(),
                cinemaDto.getPrice(),
                cinemaDto.getRoom(),
                cinemaDto.getMovie());

        cinemaRepository.save(cinema);
    }
}
