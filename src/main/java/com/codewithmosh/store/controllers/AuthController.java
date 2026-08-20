package com.codewithmosh.store.controllers;

import com.codewithmosh.store.config.JwtConfig;
import com.codewithmosh.store.dtos.JwtResponse;
import com.codewithmosh.store.dtos.LogInUserRequest;
import com.codewithmosh.store.dtos.UserDto;
import com.codewithmosh.store.entities.User;
import com.codewithmosh.store.mapper.UserMapper;
import com.codewithmosh.store.repositories.UserRepository;
import com.codewithmosh.store.services.AuthService;
import com.codewithmosh.store.services.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RequestMapping("/auth")
@RestController
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtConfig jwtConfig;
    private final AuthService authService;


    @PostMapping("/login")
        public ResponseEntity<JwtResponse> login(
                @Valid @RequestBody LogInUserRequest request,
                HttpServletResponse response
            ){

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            User user = userRepository.findByEmail(request.getEmail()).orElseThrow();


            var accessToken = jwtService.generateJwtToken(user);
            var refreshToken = jwtService.generateRefreshToken(user);

            Cookie cookie = new Cookie("refreshToken", refreshToken.toString());
            cookie.setHttpOnly(true);
            cookie.setPath("/auth/refresh");
            cookie.setMaxAge(jwtConfig.getRefreshTokenExpiration());
            cookie.setSecure(true);

            response.addCookie(cookie);

            return ResponseEntity.ok(new JwtResponse(accessToken.toString()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse> refresh(
            @CookieValue(name = "refreshToken") String refreshToken
    ){
        var jwt = jwtService.parseToken(refreshToken);
        if(jwt == null || jwt.isExpired()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Long id = jwt.getUserId();
        User user = userRepository.findById(id).orElseThrow();
        var accessToken = jwtService.generateJwtToken(user).toString();


        return ResponseEntity.ok(new JwtResponse(accessToken));

    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Void> handleBadCredentialsException(){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> me(){
        User user = authService.getCurrentUser();

       if(user == null){
           return ResponseEntity.notFound().build();
       }

       var userDto = userMapper.toDto(user);

       return ResponseEntity.ok(userDto);
    }


}
