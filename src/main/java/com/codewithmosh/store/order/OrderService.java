package com.codewithmosh.store.order;

import com.codewithmosh.store.order.dtos.OrderDto;
import com.codewithmosh.store.order.entities.Order;
import com.codewithmosh.store.auth.AuthService;
import com.codewithmosh.store.user.User;
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
