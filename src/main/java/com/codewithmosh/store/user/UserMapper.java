package com.codewithmosh.store.user;

import com.codewithmosh.store.user.dtos.RegisterUserRequest;
import com.codewithmosh.store.user.dtos.UpdateUserRequest;
import com.codewithmosh.store.user.dtos.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(RegisterUserRequest request);
    void update(UpdateUserRequest request, @MappingTarget User user);
}
