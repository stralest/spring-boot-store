package com.codewithmosh.store.order;

import com.codewithmosh.store.order.entities.Order;
import com.codewithmosh.store.user.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @EntityGraph(attributePaths = "items.product")
    @Query("SELECT o FROM Order o WHERE o.customer = :customer")
    List<Order> getOrdersByCustomer(User customer);

    @EntityGraph(attributePaths = "items.product")
    @Query("SELECT o FROM Order o Where o.id = :orderId")
    Optional<Order> getOrderWithItems(Long orderId);
}
