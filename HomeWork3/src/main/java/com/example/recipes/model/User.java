package com.example.recipes.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.*;

@Table(name = "users")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotNull
    @NonNull
    @NotEmpty
    private String name;

    @NotNull
    @NonNull
    @NotEmpty
    @Size(max = 15)
    private String username;

    @Pattern(regexp = "(.*\\d.*[^\\w].+)|(.*[^\\w].*\\d.*)", message = "Password must contain a digit and special symbol")
    @Size(min = 8)
    @NonNull
    @NotNull
    private String password;

    private String gender;

    @OneToMany(mappedBy = "creator", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Recipe> recipes = new HashSet<>();

    private Role role = Role.USER;

    private String image;

    @Size(max = 512, message = "Description should not exceed 512 characters")
    private String description;

    private Status status = Status.ACTIVE;

    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime created = LocalDateTime.now();
    @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime modified = LocalDateTime.now();

    public User(@NotNull @NonNull String name, @NotNull @NonNull @Size(max = 15) String username, @Pattern(regexp = "(.*\\d.*[^\\w].+)|(.*[^\\w].*\\d.*)", message = "Password must contain a digit and special symbol") @NonNull @NotNull String password,
                Role role) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.role = role;
    }


    public User(@NotNull @NonNull String name,
                @NotNull @NonNull @Size(max = 15) String username,
                @Pattern(regexp = "(.*\\d.*[^\\w].+)|(.*[^\\w].*\\d.*)", message = "Password must contain a digit and special symbol") @NonNull @NotNull String password,
                Role role,
                String description) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.role = role;
        this.description = description;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority simpleGrantedAuthority =
                new SimpleGrantedAuthority(String.format("ROLE_%s", role.toString()));
        return List.of(simpleGrantedAuthority);
    }

    @Override
    public boolean isAccountNonExpired() {
        return status == Status.ACTIVE;
    }

    @Override
    public boolean isAccountNonLocked() {
        return status == Status.ACTIVE;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return status == Status.ACTIVE;
    }

    @Override
    public boolean isEnabled() {
        return status == Status.ACTIVE;
    }

    public boolean isInRole(String role) {
        return this.role.toString().equals(role);
    }
}
