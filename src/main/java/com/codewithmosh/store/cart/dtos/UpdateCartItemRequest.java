package com.codewithmosh.store.cart.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateCartItemRequest {
    @NotNull
    @Min(value = 1, message="Quantity must be greater than 0")
    @Max(value = 1000, message="Quantity must be less or equal to 1000")

    private Integer quantity;
}
