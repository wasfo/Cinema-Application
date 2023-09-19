package org.com.service;


import org.com.entity.Cinema;
import org.com.repository.CinemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CinemaService {

    private CinemaRepository cinemaRepository;

    @Autowired
    public CinemaService(CinemaRepository cinemaRepository) {
        this.cinemaRepository = cinemaRepository;
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
