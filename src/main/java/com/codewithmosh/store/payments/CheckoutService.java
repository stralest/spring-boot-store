package com.codewithmosh.store.payments;

import com.codewithmosh.store.cart.entities.Cart;
import com.codewithmosh.store.entities.Order;
import com.codewithmosh.store.user.User;
import com.codewithmosh.store.cart.exceptions.CartNotFoundException;
import com.codewithmosh.store.cart.exceptions.EmptyCartException;
import com.codewithmosh.store.cart.CartRepository;
import com.codewithmosh.store.repositories.OrderRepository;
import com.codewithmosh.store.services.AuthService;
import com.codewithmosh.store.cart.CartService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@AllArgsConstructor
@Service
public class CheckoutService {
    private final CartRepository cartRepository;
    private final AuthService authService;
    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final PaymentGateway paymentGateway;

    @Transactional
    public CheckoutResponse checkout(CheckoutRequest request) {
        Cart cart = cartRepository.getCartWithItems(request.getCartId()).orElse(null);

        if(cart == null){
            throw new CartNotFoundException();
        }

        if(cart.isEmpty()){
            throw new EmptyCartException();
        }

        User user = authService.getCurrentUser();

        Order order =  Order.fromCart(cart, user);

        orderRepository.save(order);

        try{
            var session = paymentGateway.createCheckoutSession(order);


            cartService.clearCart(cart.getId());

            return new CheckoutResponse(order.getId(), session.getCheckoutUrl());

        }catch(PaymentException ex){
            orderRepository.delete(order);
            throw ex;
        }
    }

    public void handleWebhookEvent(WebhookRequest request){
        paymentGateway.parseWeebhookRequest(request)
                .ifPresent(paymentResult -> {
                    var order = orderRepository.findById(Long.valueOf(paymentResult.getOrderId())).orElseThrow();
                    order.setStatus(paymentResult.getOrderStatus());
                    orderRepository.save(order);
                });
    }
}
