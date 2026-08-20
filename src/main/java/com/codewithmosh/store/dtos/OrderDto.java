package com.codewithmosh.store.dtos;

import com.codewithmosh.store.entities.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderDto {
    private Long id;
    private OrderStatus orderStatus;
    private LocalDateTime createdAt;
    private List<OrderItemDto> items = new ArrayList<>();
    private BigDecimal totalPrice;
}
