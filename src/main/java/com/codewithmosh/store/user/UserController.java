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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;


@AllArgsConstructor
@RestController
@RequestMapping("/users")
@Tag(name = "Users")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @GetMapping
    public List<UserDto> getUsers(@RequestParam(required = false, defaultValue = "", name = "sort") String sortBy){
        return userService.getAllUsers(sortBy).stream().map(u -> userMapper.toDto(u)).toList();
    }


    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long userId) {
        User user = userService.getUserById(userId);

        if(user == null){
            return ResponseEntity.notFound().build();
        }

        UserDto resposneUser = userMapper.toDto(user);

        return ResponseEntity.ok(resposneUser);
    }

    @PostMapping
    public ResponseEntity<?> createUser(
            UriComponentsBuilder uriBuilder,
            @Valid @RequestBody RegisterUserRequest request) {

        if(userRepository.existsByEmail(request.getEmail())){
            return ResponseEntity.badRequest().body(Map.of("email", "Email is already registered!"));
        }

        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);

        UserDto userDto = userMapper.toDto(user);
        var path = uriBuilder.path("/users/{id}").buildAndExpand(userDto.getId()).toUri();

        return ResponseEntity.created(path).body(userDto);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("userId") Long userId, @RequestBody UpdateUserRequest request){
        User user = userRepository.findById(userId).orElse(null);

        if(user == null){
            return ResponseEntity.notFound().build();
        }

        userMapper.update(request, user);

        userRepository.save(user);


        return ResponseEntity.ok(userMapper.toDto(user));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") Long userId){
        User user = userRepository.findById(userId).orElse(null);

        if(user == null){
            return ResponseEntity.notFound().build();
        }

        userRepository.deleteById(userId);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{userId}/change-password")
    public ResponseEntity<Void> changePassword(
            @PathVariable("userId") Long userId,
            @RequestBody UpdatePasswordRequest request
            ){
        User user = userRepository.findById(userId).orElse(null);

        if(user == null){
            return ResponseEntity.notFound().build();
        }

        if(!user.getPassword().equals(request.getOldPassword())){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        user.setPassword(request.getNewPassword());
        userRepository.save(user);

        return ResponseEntity.noContent().build();
    }


}
