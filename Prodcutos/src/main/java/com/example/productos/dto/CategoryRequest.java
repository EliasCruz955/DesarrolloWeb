package com.example.productos.dto;

import jakarta.validation.constraints.NotBlank;

public class CategoryRequest {

        Long id;
        @NotBlank(message = "name is mandatory")
        String name;

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public Boolean getActive() {
                return active;
        }

        public void setActive(Boolean active) {
                this.active = active;
        }

        Boolean active = true;
}
