package com.codewithmosh.store.common;

import com.codewithmosh.store.cart.exceptions.CartNotFoundException;
import com.codewithmosh.store.cart.exceptions.EmptyCartException;
import com.codewithmosh.store.common.dtos.ErrorDto;
import com.codewithmosh.store.order.OrderNotFoundException;
import com.codewithmosh.store.payments.PaymentException;
import com.codewithmosh.store.product.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorDto> handleUnreadableMessage(){
        return ResponseEntity.badRequest()
                .body(new ErrorDto("Invalid request body"));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException exception){
        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult().getFieldErrors().forEach(e -> errors.put(e.getField(), e.getDefaultMessage()));

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<ErrorDto> handleCartNotFound(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorDto("Cart not found!"));
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorDto> handleProductNotFound(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorDto("Product not found!"));
    }

    @ExceptionHandler(EmptyCartException.class)
    public ResponseEntity<ErrorDto> handleEmptyCart(){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorDto("Cart is empty!"));
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<ErrorDto> handleOrderNotFound(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorDto("Order not found!"));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorDto> handleAccessDenied(Exception ex){
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ErrorDto(ex.getMessage()));
    }

    @ExceptionHandler(PaymentException.class)
    public ResponseEntity<ErrorDto> handlePayment(){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorDto("Error creating a checkout session!"));
    }
}
