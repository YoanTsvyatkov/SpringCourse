package com.fmi.reviews.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NonNull
    @NotNull
    @Column(unique = true)
    private String username;

    private String firstName;

    private String lastName;

    @NonNull
    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Size(min = 8, message = "Password must be at least 8 symbols")
    private String password;

    private String photo;

    private String email;

    private Role role = Role.REVIEWER;

    private Status status = Status.ACTIVE;

    private Date created = new Date();

    private Date modified = new Date();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<MovieReview> reviews = new HashSet<>();

    public User(@NonNull @NotNull String username, String firstName, String lastName, String password, String email, Role role,
                Set<MovieReview> reviews) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
        this.password = password;
        this.reviews = reviews;
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(String.format("ROLE_%s", role.toString())));
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return status == Status.ACTIVE ||
                status == Status.DEACTIVATED;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return status == Status.ACTIVE;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return status == Status.ACTIVE;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return status == Status.ACTIVE;
    }
}
