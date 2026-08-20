package com.codewithmosh.store.controllers;

import com.codewithmosh.store.dtos.AddItemToCartRequest;
import com.codewithmosh.store.dtos.CartDto;
import com.codewithmosh.store.dtos.CartItemDto;
import com.codewithmosh.store.dtos.UpdateCartItemRequest;
import com.codewithmosh.store.services.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/carts")
@Tag(name = "Carts")
public class CartController {
    private final CartService cartService;


    @PostMapping
    @Operation(summary = "Creating new cart")
    public ResponseEntity<CartDto> createCart(
    ){
        CartDto cartDto = cartService.createCart();

        return ResponseEntity.status(HttpStatus.CREATED).body(cartDto);
    }


    @PostMapping("/{cartId}/items")
    @Operation(summary = "Adding a item to a cart")
    public ResponseEntity<?> addToCart(
         @PathVariable(name = "cartId") UUID cartId,
         @RequestBody AddItemToCartRequest request
    ){
        CartItemDto cartItemDto = cartService.addToCart(cartId, request.getProductId());

        return ResponseEntity.status(HttpStatus.CREATED).body(cartItemDto);
    }

    @GetMapping("/{cartId}")
    @Operation(summary = "Getting a cart")
    public ResponseEntity<CartDto> getCart(@PathVariable("cartId") UUID cartId){

        CartDto cartDto = cartService.getCart(cartId);

        return ResponseEntity.ok(cartDto);
    }

    @PutMapping("/{cartId}/items/{productId}")
    @Operation(summary = "Updating Cart by product quantity")
    public ResponseEntity<?> updateCart(
            @PathVariable(name = "cartId") UUID cartId,
            @PathVariable(name = "productId") Long productId,
            @Valid @RequestBody UpdateCartItemRequest request
    ){

        CartItemDto cartItemDto = cartService.updateCart(cartId, productId, request.getQuantity());

        return ResponseEntity.ok(cartItemDto);
    }

    @DeleteMapping("/{cartId}/items/{productId}")
    @Operation(summary = "Removing a product from a cart")
    public ResponseEntity<?> removeFromCart(
            @PathVariable(name = "cartId") UUID cartId,
            @PathVariable(name = "productId") Long productId
    ){
        cartService.removeFromCart(cartId, productId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{cartId}/items")
    @Operation(summary = "Removing all items from a cart")
    public ResponseEntity<?> clearCart(@PathVariable("cartId") UUID cartId){

        cartService.clearCart(cartId);
        return ResponseEntity.noContent().build();
    }



}
