package com.fmi.reviews.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "reviews")
public class  MovieReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @NotNull
    @Size(max = 1024)
    private String text;

    @NonNull
    @NotNull
    @Min(1)
    @Max(10)
    private int rating;

    @NonNull
    @NotNull
    @Size(max = 80)
    private String reviewTitle;

    @JsonIgnore
    @ManyToOne
    private Movie movie;

    @JsonIgnore
    @ManyToOne
    private User user;

    private Date created = new Date();
    private Date modified = new Date();
}
