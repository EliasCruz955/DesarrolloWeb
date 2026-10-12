package com.example.productos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record ProductRequest(
        Long id,
        @NotBlank(message = "name is mandatory")
        String name,
        @NotNull
        @Positive
        Double price,
        @NotNull
        @PositiveOrZero
        Integer stock
) {

}
