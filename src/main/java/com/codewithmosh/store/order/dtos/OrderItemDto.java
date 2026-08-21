package com.codewithmosh.store.order.dtos;

import com.codewithmosh.store.cart.dtos.CartProductDto;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemDto {
    private CartProductDto product;
    private Integer quantity;
    private BigDecimal totalPrice;
}
