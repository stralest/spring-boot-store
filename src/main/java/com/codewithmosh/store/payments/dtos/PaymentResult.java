package com.codewithmosh.store.payments.dtos;

import com.codewithmosh.store.order.entities.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PaymentResult {
    private Long orderId;
    private OrderStatus orderStatus;
}
