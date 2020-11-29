package com.fmi.recipes.model;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class ErrorResponse {
    @NonNull
    private int code;
    @NonNull
    private String message;

    private List<String> violation;
}
