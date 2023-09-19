package org.com.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.springframework.context.annotation.DependsOn;

import java.util.List;

@Entity
@Data
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "duration")
    private short durationInMinutes;

    @Column(name = "rating")
    private float rating;

    @OneToMany(mappedBy = "movie")
    private List<Cinema> cinemas;

    public Movie(String name, short durationInMinutes, float rating) {
        this.name = name;
        this.durationInMinutes = durationInMinutes;
        this.rating = rating;
    }
}
