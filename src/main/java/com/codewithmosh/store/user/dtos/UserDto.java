package com.codewithmosh.store.user.dtos;

import com.codewithmosh.store.user.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private Role role;
}
