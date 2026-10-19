package com.example.productos.dto;

import jakarta.validation.constraints.NotBlank;

public record CategoryRequest(
        Long id,
        @NotBlank(message = "name is mandatory")
        String name
) {
}
