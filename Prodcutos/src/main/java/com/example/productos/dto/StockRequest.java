package com.example.productos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record StockRequest(
        @NotBlank(message = "word is mandatory")
        String word,
        @NotNull
        @PositiveOrZero
        Integer cantidad
) {
}
