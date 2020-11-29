package com.fmi.recipes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.validator.constraints.URL;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Document(collection = "users")
public class User implements UserDetails {
    @Id
    @Size(max = 24)
    private String id;

    private String name;

    @NotNull
    @NonNull
    @Pattern(regexp = "[A-Za-z]+", message = "Username should only contain characters")
    @Size(max = 15, message = "Username can`t be more than 15 symbols")
    private String username;

    @NonNull
    @NotNull
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Pattern(regexp = "(\\w*\\d+\\w*[^A-Za-z\\d]+\\w*|\\w*[^A-Za-z\\d]+\\w*\\d+\\w*)",
            message = "Password must contain 1 digit, and one special symbol")
    @Size(min = 8, message = "Password must be at least 8 symbols")
    private String password;

    private String gender;

    private Role role;

    @URL
    private String photo;

    @Size(max = 512)
    private String profileDescription;

    private Status status = Status.ACTIVE;

    private Date created = new Date();

    private Date modified = new Date();

    public User(String username, String password, String name, Role role, String photo,
                String profileDescription, Status status) {
        setUsername(username);
        setPassword(password);
        setName(name);
        setRole(role);
        setProfileDescription(profileDescription);
        setStatus(status);
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
