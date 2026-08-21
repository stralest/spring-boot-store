package com.codewithmosh.store.cart.entities;

import com.codewithmosh.store.entities.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "carts")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "date_created", insertable = false, updatable = false)
    private LocalDate dateCreated;

    @OneToMany(mappedBy = "cart", cascade = {CascadeType.MERGE}, orphanRemoval = true)
    private Set<CartItem> items = new LinkedHashSet<>();

    public BigDecimal getTotalPrice(){
        return items.stream().map(item -> item.getTotalPrice())
                .reduce(BigDecimal.ZERO, (total, price) -> total.add(price));
    }

    public CartItem findCartItem(Long productId){
       return items.stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);
    }

    public CartItem addItem(Product product){
        CartItem cartItem = findCartItem(product.getId());

        if (cartItem != null){
            cartItem.setQuantity(cartItem.getQuantity() + 1);
        }
        else{
            cartItem = new CartItem();
            cartItem.setCart(this);
            cartItem.setProduct(product);
            cartItem.setQuantity(1);

            items.add(cartItem);
        }
        return cartItem;
    }

    public void setQuantityToCartItem(CartItem cartItem, Integer quantity){
        cartItem.setQuantity(quantity);
    }

    public void removeItemFromCart(Long productId){
        CartItem cartItem = findCartItem(productId);
        if(cartItem != null){
            items.remove(cartItem);
            cartItem.setCart(null);
        }
    }

    public void clearCart(){
        items.clear();
    }

    public boolean isEmpty(){
        return items.isEmpty();
    }
}
