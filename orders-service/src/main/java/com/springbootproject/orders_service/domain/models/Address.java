package com.springbootproject.orders_service.domain.models;

import jakarta.validation.constraints.NotBlank;

public record Address(
    @NotBlank(message = "address is required") String addressLine1,
    String addressLine2,
    @NotBlank(message = "city is required") String city,
    @NotBlank(message = "city is required") String state,
    @NotBlank(message = "city is required") String zipcode,
    @NotBlank(message = "city is required") String country) {}
