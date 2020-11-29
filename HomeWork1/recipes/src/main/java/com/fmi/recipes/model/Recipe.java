package com.fmi.recipes.model;

import lombok.*;
import org.hibernate.validator.constraints.URL;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Document(collection = "recipes")
public class Recipe {
    @Id
    @Size(max = 24)
    private String id;


    @Size(max = 24)
    private String userId;

    @NonNull
    @NotNull
    @Size(max = 80)
    private String recipeName;

    @NonNull
    @NotNull
    @Size(max = 256)
    private String description;

    @NonNull
    @NotNull
    private int timeOfCooking;

    @NonNull
    @NotNull
    private List<String> products = new ArrayList<>();

    @NotNull
    @NonNull
    @URL
    private String url;

    @NotNull
    @NonNull
    @Size(max = 2048)
    private String additionalInfo;

    private Set<String> tags = new HashSet<>();

    private Date created = new Date();

    private Date modified = new Date();

    public Recipe(@NonNull
                  @NotNull
                  @Size(max = 24) String userId,
                  @NonNull
                  @NotNull
                  @Size(max = 80) String recipeName,
                  @NonNull
                  @NotNull
                  @Size(max = 256) String description,
                  @NonNull
                  @NotNull
                          int timeOfCooking,
                  @NonNull
                  @NotNull
                          List<String> products,
                  @NotNull
                  @NonNull
                  @URL
                          String url,
                  @NotNull
                  @NonNull
                  @Size(max = 2048)
                          String additionalInfo) {
        this.userId = userId;
        this.recipeName = recipeName;
        this.description = description;
        this.products = products;
        this.url = url;
        this.additionalInfo = additionalInfo;
        this.timeOfCooking = timeOfCooking;
    }
}
