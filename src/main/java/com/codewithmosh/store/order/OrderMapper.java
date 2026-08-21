package com.codewithmosh.store.order;

import com.codewithmosh.store.order.dtos.OrderDto;
import com.codewithmosh.store.order.dtos.OrderItemDto;
import com.codewithmosh.store.order.entities.Order;
import com.codewithmosh.store.order.entities.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(target = "orderStatus", expression = "java(order.getStatus())")
    OrderDto toDto(Order order);

    OrderItemDto toDto(OrderItem orderItem);

    List<OrderDto> toDto(List<Order> orders);
}
