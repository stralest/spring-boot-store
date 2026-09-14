package com.codewithmosh.store.user;

import com.codewithmosh.store.user.dtos.RegisterUserRequest;
import com.codewithmosh.store.user.dtos.UpdatePasswordRequest;
import com.codewithmosh.store.user.dtos.UpdateUserRequest;
import com.codewithmosh.store.user.dtos.UserDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@AllArgsConstructor
@RestController
@RequestMapping("/users")
@Tag(name = "Users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<UserDto> getUsers(@RequestParam(required = false, defaultValue = "", name = "sort") String sortBy){
        return userService.getAllUsers(sortBy);
    }


    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long userId) {
        var user = userService.getUserById(userId);

        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<?> createUser(
            @Valid @RequestBody RegisterUserRequest request) {

        var user = userService.createUser(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("userId") Long userId, @RequestBody UpdateUserRequest request){
        var user = userService.updateUser(userId, request);

        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") Long userId){
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{userId}/change-password")
    public ResponseEntity<Void> changePassword(
            @PathVariable("userId") Long userId,
            @RequestBody UpdatePasswordRequest request
            ){
        userService.changePassword(userId, request);

        return ResponseEntity.noContent().build();
    }


}
