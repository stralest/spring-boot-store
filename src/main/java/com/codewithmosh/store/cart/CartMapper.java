package com.codewithmosh.store.cart;

import com.codewithmosh.store.cart.dtos.CartDto;
import com.codewithmosh.store.cart.dtos.CartItemDto;
import com.codewithmosh.store.cart.entities.Cart;
import com.codewithmosh.store.cart.entities.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {
    @Mapping(target = "totalPrice", expression = "java(cart.getTotalPrice())")
    CartDto toDto(Cart cart);

    @Mapping(target = "totalPrice", expression = "java(cartItem.getTotalPrice())")
    CartItemDto toDto(CartItem cartItem);
}
