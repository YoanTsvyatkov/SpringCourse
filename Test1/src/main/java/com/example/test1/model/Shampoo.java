package com.example.test1.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Table(name = "shampoos")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@ToString
public class Shampoo extends BaseEntity {
    private String brand;
    private double price;
    private int size;

    @ManyToOne
    private Label label;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "shampoos_ingredients",
            joinColumns = @JoinColumn(name = "shampoo_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id", referencedColumnName = "id"))
    private Set<Ingredient> ingredients = new HashSet<>();

    @Override
    public String toString() {
        return "Shampoo{" +
                "brand='" + brand + '\'' +
                ", price=" + price +
                ", size=" + size +
                ", label=" + label +
                '}';
    }
}
