package com.example.serialist.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "movies")
@Data
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String description;
    @Lob
    private byte[] image;
   /* @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    private final List<User> userList = new ArrayList<>();*/
}
