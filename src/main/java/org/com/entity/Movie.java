package org.com.entity;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.context.annotation.DependsOn;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "duration")
    private short durationInMinutes;

    @Column(name = "rating")
    private float rating;

    @Column(name = "genre")
    private String genre;

    @Column(name = "director")
    private String director;

    @Column(name = "imageName")
    private String imageName;

    @OneToMany(mappedBy = "movie", fetch = FetchType.EAGER)
    private List<Cinema> cinemas;

    public Movie(String name, short durationInMinutes, float rating, String genre, String director) {
        this.name = name;
        this.durationInMinutes = durationInMinutes;
        this.rating = rating;
        this.genre = genre;
        this.director = director;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "name='" + name + '\'' +
                ", durationInMinutes=" + durationInMinutes +
                ", rating=" + rating +
                '}';
    }
}
