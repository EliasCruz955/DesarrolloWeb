package com.example.productos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record ProductResponse(
        Long id,
        @NotBlank
        String name,
        @Positive
        Double price,
        @PositiveOrZero
        int stock
) {
}
