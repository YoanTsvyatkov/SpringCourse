package com.example.test1.model;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Table(name = "labels")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
public class Label extends BaseEntity{
    @NonNull
    @NotNull
    private String title;

    @NonNull
    @NotNull
    private String subtitle;

    @OneToMany(mappedBy = "label", fetch = FetchType.EAGER)
    private Set<Shampoo> shampoos;

    @Override
    public String toString() {
        return "Label{" +
                "title='" + title + '\'' +
                ", subtitle='" + subtitle + '\'' +
                '}';
    }
}
