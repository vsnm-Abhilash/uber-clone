package com.abhilash.project.uber.uberApp.security;

import com.abhilash.project.uber.uberApp.entities.User;
import com.abhilash.project.uber.uberApp.exceptions.ResourceNotFoundException;
import com.abhilash.project.uber.uberApp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(()->new ResourceNotFoundException("User with email "+username+ " not found!"));
    }

    public User getUserByUserId(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                ()->new ResourceNotFoundException("User not found with Id "+userId)
        );
    }

}
