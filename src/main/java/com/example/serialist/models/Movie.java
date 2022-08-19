package com.example.serialist.models;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String description;
    @Lob
    private byte[] image;
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY, mappedBy = "movieList")
    @EqualsAndHashCode.Exclude @ToString.Exclude
    private Set<User> inUsersList;

    public Movie() {
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Movie;
    }

}
