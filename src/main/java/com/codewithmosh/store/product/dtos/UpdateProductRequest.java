package com.codewithmosh.store.product.dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateProductRequest {
    private String name;
    private BigDecimal price;
    private String description;
    private Byte categoryId;
}
