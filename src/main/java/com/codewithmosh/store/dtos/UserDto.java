package com.codewithmosh.store.dtos;

import com.codewithmosh.store.entities.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private Role role;
}
