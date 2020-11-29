package com.fmi.recipes.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Credentials {
    @NotNull
    @NonNull
    @Size(max = 15, message = "Username can`t be more than 15 symbols")
    private String username;
    @NotNull
    @NonNull
    @Pattern(regexp = "(\\w*\\d+\\w*[^A-Za-z\\d]+\\w*|\\w*[^A-Za-z\\d]+\\w*\\d+\\w*)",
            message = "Password must contain 1 digit, and one symbol")
    @Size(min = 8, message = "Password must be at least 8 symbols")
    private String password;
}
