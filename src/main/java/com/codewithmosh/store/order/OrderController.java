package com.codewithmosh.store.order;

import com.codewithmosh.store.order.dtos.OrderDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;


    @GetMapping("/{orderId}")
    public OrderDto getOrder(@PathVariable("orderId") Long orderId){
        return orderService.getSingleOrder(orderId);
    }

    @GetMapping
    public List<OrderDto> getAllOrders(){
        return orderService.getAllOrders();
    }
}
