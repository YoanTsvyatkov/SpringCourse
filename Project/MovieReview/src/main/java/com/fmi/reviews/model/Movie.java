package com.fmi.reviews.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @NotNull
    private String movieTitle;

    @NonNull
    @NotNull
    private String description;

    private String moviePhoto;

    private Date releaseDate;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<MovieReview> reviews = new HashSet<>();

    private Date created = new Date();

    private Date modified = new Date();

    public Movie(@NonNull @NotNull String movieTitle, @NonNull @NotNull String description, Set<MovieReview> reviews) {
        this.movieTitle = movieTitle;
        this.description = description;
        this.reviews = reviews;
    }
}
