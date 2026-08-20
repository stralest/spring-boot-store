package com.codewithmosh.store.payments;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/checkout")
@RestController
public class CheckoutController {
    private final CheckoutService checkoutService;

    @PostMapping
    public ResponseEntity<CheckoutResponse> checkout(@Valid @RequestBody CheckoutRequest request) {
            return ResponseEntity.ok(checkoutService.checkout(request));
    }

    @PostMapping("/webhook")
    public void handleWebhook(
       @RequestHeader Map<String, String> headers,
       @RequestBody String payload
    ){

       checkoutService.handleWebhookEvent(new WebhookRequest(headers, payload));


    }

}

