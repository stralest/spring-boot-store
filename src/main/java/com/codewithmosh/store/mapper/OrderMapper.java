package com.codewithmosh.store.mapper;

import com.codewithmosh.store.dtos.OrderDto;
import com.codewithmosh.store.dtos.OrderItemDto;
import com.codewithmosh.store.entities.Order;
import com.codewithmosh.store.entities.OrderItem;
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
