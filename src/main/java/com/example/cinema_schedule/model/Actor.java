package com.example.cinema_schedule.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Actors")
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "actor")
    private List<Movie_Actor> movie_actorList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Movie_Actor> getMovie_actorList() {
        return movie_actorList;
    }

    public void setMovie_actorList(List<Movie_Actor> movie_actorList) {
        this.movie_actorList = movie_actorList;
    }
}
