package com.example.recipes.model;


import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.*;

@Table(name = "recipes")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User creator;

    @Size(max = 80)
    @NotNull
    @NonNull
    @NotEmpty
    private String recipeName;

    @Size(max = 256)
    @NotNull
    @NonNull
    @NotEmpty
    private String shortDescription;

    @NotNull
    @NonNull
    @Positive
    private int timeOfPreparation;

    @ElementCollection
    private List<String> ingredients = new ArrayList<>();

    private String photo;

    @Size(max = 2048)
    @NotNull
    @NonNull
    @NotEmpty
    private String fullDescription;

    @ElementCollection
    private Set<String> tags = new HashSet<>();

    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime created = LocalDateTime.now();

    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime modified = LocalDateTime.now();
}
