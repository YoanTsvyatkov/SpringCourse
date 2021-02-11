package com.example.test1.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Table(name = "ingredients")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
public class Ingredient extends BaseEntity {
    private String name;
    private double price;

    @ManyToMany(mappedBy = "ingredients", fetch = FetchType.EAGER)
    private Set<Shampoo> shampoos = new HashSet<>();

    @Override
    public String toString() {
        return "Ingredient{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
