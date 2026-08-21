package com.codewithmosh.store.payments.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CheckoutRequest {
    @NotNull(message = "CartId is required!")
    private UUID cartId;
}
