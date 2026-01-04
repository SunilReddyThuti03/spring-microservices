package com.springbootproject.orders_service.domain.models;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record Customer(
        @NotBlank(message = "Customer name is requrired") String name,
        @NotBlank(message = "Customer email is required") @Email String email,
        @NotBlank(message = "Cusotmer phone number is required") String phone
) {
}
