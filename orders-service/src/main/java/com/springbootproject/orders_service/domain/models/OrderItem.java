package com.springbootproject.orders_service.domain.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record OrderItem(
@NotBlank(message = "code is required") String code,
@NotBlank(message = "name is required") String name,
@NotNull(message = "price is null") BigDecimal price,
@NotNull(message = "Quantity cannot be null") @Min(value = 1, message = "Minimum quantity must be 1") Integer quantity
) {
}
