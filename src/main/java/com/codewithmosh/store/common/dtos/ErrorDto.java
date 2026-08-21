package com.codewithmosh.store.common.dtos;

import lombok.Data;

@Data
public class ErrorDto {
    private String error;

    public ErrorDto(String error) {
        this.error = error;
    }
}
