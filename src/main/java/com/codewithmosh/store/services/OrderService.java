package com.codewithmosh.store.services;

import com.codewithmosh.store.dtos.OrderDto;
import com.codewithmosh.store.entities.Order;
import com.codewithmosh.store.entities.User;
import com.codewithmosh.store.exceptions.OrderNotFoundException;
import com.codewithmosh.store.mapper.OrderMapper;
import com.codewithmosh.store.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final AuthService authService;

    public OrderDto getSingleOrder(Long orderId){
        Order order = orderRepository.getOrderWithItems(orderId).orElseThrow(OrderNotFoundException::new);

        User user = authService.getCurrentUser();

        if(!order.isPlacedBy(user)){
            throw new AccessDeniedException("You dont have access to this order!");
        }

        return orderMapper.toDto(order);
    }

    public List<OrderDto> getAllOrders(){
        var user = authService.getCurrentUser();
        var orders = orderRepository.getOrdersByCustomer(user);

        return orderMapper.toDto(orders);
    }
}
