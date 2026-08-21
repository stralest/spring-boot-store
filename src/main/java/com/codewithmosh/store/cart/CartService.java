package com.codewithmosh.store.cart;

import com.codewithmosh.store.cart.dtos.CartDto;
import com.codewithmosh.store.cart.dtos.CartItemDto;
import com.codewithmosh.store.cart.entities.Cart;
import com.codewithmosh.store.cart.entities.CartItem;
import com.codewithmosh.store.product.Product;
import com.codewithmosh.store.cart.exceptions.CartNotFoundException;
import com.codewithmosh.store.product.ProductNotFoundException;
import com.codewithmosh.store.product.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.UUID;

@AllArgsConstructor
@Service
public class CartService {
    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final ProductRepository productRepository;

    public CartDto createCart(){
        Cart cart = new Cart();

        cartRepository.save(cart);

        return cartMapper.toDto(cart);
    }

    public CartItemDto addToCart(UUID cartId, Long productId){
        Cart cart = cartRepository.getCartWithItems(cartId).orElseThrow(CartNotFoundException::new);

        Product product = productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);

        CartItem cartItem = cart.addItem(product);

        cartRepository.save(cart);

        return cartMapper.toDto(cartItem);
    }

    public CartDto getCart(UUID cartId){
        Cart cart = cartRepository.getCartWithItems(cartId).orElseThrow(CartNotFoundException::new);

        return cartMapper.toDto(cart);
    }

    public CartItemDto updateCart(UUID cartId, Long productId, Integer quantity){
        Cart cart = cartRepository.findById(cartId).orElseThrow(CartNotFoundException::new);

        CartItem cartItem = cart.findCartItem(productId);

        if(cartItem == null){
            throw new ProductNotFoundException();
        }

        cart.setQuantityToCartItem(cartItem, quantity);

        cartRepository.save(cart);

        return cartMapper.toDto(cartItem);
    }

    public void removeFromCart(UUID cartId, Long productId){
        Cart cart = cartRepository.findById(cartId).orElseThrow(CartNotFoundException::new);

        cart.removeItemFromCart(productId);

        cartRepository.save(cart);
    }

    public void clearCart(UUID cartId){
        Cart cart = cartRepository.findById(cartId).orElseThrow(CartNotFoundException::new);

        cart.clearCart();

        cartRepository.save(cart);
    }
}
