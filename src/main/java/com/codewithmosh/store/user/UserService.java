package com.codewithmosh.store.user;

import com.codewithmosh.store.user.Exceptions.EmailAlreadyExistsException;
import com.codewithmosh.store.user.Exceptions.InvalidPasswordException;
import com.codewithmosh.store.user.Exceptions.UserNotFoundException;
import com.codewithmosh.store.user.dtos.RegisterUserRequest;
import com.codewithmosh.store.user.dtos.UpdatePasswordRequest;
import com.codewithmosh.store.user.dtos.UpdateUserRequest;
import com.codewithmosh.store.user.dtos.UserDto;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;


    public List<UserDto> getAllUsers(String sort){
        if(!Set.of("id", "name", "email").contains(sort)){
            sort = "name";
        }

        var users = userRepository.findAll(Sort.by(sort));

        return users.stream().map(userMapper::toDto).toList();
    }

    public UserDto getUserById(Long userId){
        var user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        return userMapper.toDto(user);
    }

    public UserDto createUser(RegisterUserRequest request){
        if(userRepository.existsByEmail(request.getEmail())){
            throw new EmailAlreadyExistsException(request.getEmail());
        }

        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);
        userRepository.save(user);

        return userMapper.toDto(user);
    }

    @Transactional
    public UserDto updateUser(Long userId, UpdateUserRequest request){
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        userMapper.update(request, user);

        return userMapper.toDto(user);
    }

    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(userId);
        }

        userRepository.deleteById(userId);
    }

    @Transactional
    public void changePassword(Long userId, UpdatePasswordRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new InvalidPasswordException();
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found!"));

       return new org.springframework.security.core.userdetails.User(
               user.getEmail(),
               user.getPassword(),
               Collections.emptyList()
       );
    }
}
