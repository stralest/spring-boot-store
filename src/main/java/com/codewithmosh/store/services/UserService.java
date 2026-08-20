package com.codewithmosh.store.services;

import com.codewithmosh.store.entities.User;
import com.codewithmosh.store.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.Collections;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;


    public List<User> getAllUsers(String sort){
        if(!Set.of("id", "name", "email").contains(sort)){
            sort = "name";
        }

        return userRepository.findAll(Sort.by(sort));
    }

    public User getUserById(Long userId){
        return userRepository.findById(userId).orElse(null);
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
