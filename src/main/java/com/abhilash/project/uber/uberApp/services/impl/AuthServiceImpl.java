package com.abhilash.project.uber.uberApp.services.impl;

import com.abhilash.project.uber.uberApp.dto.*;
import com.abhilash.project.uber.uberApp.entities.Driver;
import com.abhilash.project.uber.uberApp.entities.User;
import com.abhilash.project.uber.uberApp.entities.enums.Role;
import com.abhilash.project.uber.uberApp.exceptions.ResourceNotFoundException;
import com.abhilash.project.uber.uberApp.exceptions.RuntimeConflictException;
import com.abhilash.project.uber.uberApp.repositories.UserRepository;
import com.abhilash.project.uber.uberApp.security.JWTService;
import com.abhilash.project.uber.uberApp.security.UserService;
import com.abhilash.project.uber.uberApp.services.AuthService;
import com.abhilash.project.uber.uberApp.services.DriverService;
import com.abhilash.project.uber.uberApp.services.RiderService;
import com.abhilash.project.uber.uberApp.services.WalletService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final ModelMapper mapper;

    private final UserRepository userRepository;

    private final RiderService riderService;
    private final WalletService walletService;
    private final DriverService driverService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final UserService userService;
    @Override
    @Transactional
    public UserDTO signUp(SignUpDTO signUpDTO) {
        User user=userRepository.findByEmail(signUpDTO.getEmail()).orElse(null);
        if(user!=null){
            throw new RuntimeConflictException("Cannot signup, User already exists with email "+signUpDTO.getEmail());
        }

        User mappedUser =mapper.map(signUpDTO,User.class);
        mappedUser.setRoles(Set.of(Role.RIDER));
        mappedUser.setPassword(passwordEncoder.encode(mappedUser.getPassword()));
        User savedUser=userRepository.save(mappedUser);

        //create User related Entities
        riderService.createNewRider(savedUser);
        walletService.createNewWallet(savedUser);
        return mapper.map(savedUser,UserDTO.class);
    }
    @Override
    public DriverDTO onBoardNewDriver(Long userId,String vehicleId) {
        User user=userRepository.findById(userId).orElseThrow(
                ()->new ResourceNotFoundException("User with id "+userId+" not found!")
        );

        if(user.getRoles().contains(Role.DRIVER)){
            throw new RuntimeConflictException("User with id "+userId+" is already a Driver!");
        }

        Driver driver=driverService.findByVehicleId(vehicleId);
        if(driver!=null)
        {
            if(driver.getUser().getId().equals(userId)){
                throw new RuntimeConflictException("User with id "+userId+" is already a Driver!");

            }
            else{
                throw new RuntimeConflictException("Vehicle with id "+vehicleId+" has already been registered with a different Driver!");
            }
        }
        Driver createDriver=Driver.builder()
                .available(true)
                .user(user)
                .rating(0.0)
                .vehicleId(vehicleId)
                .build();
        user.getRoles().add(Role.DRIVER);
        userRepository.save(user);
        return driverService.createNewDriver(createDriver);
    }

    @Override
    public String[] login(LoginRequestDTO loginRequestDTO) {
        Authentication authentication=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmail(),loginRequestDTO.getPassword())
        );
        User user= (User) authentication.getPrincipal();
        String accessToken= jwtService.generateAccessToken(user);
        String refreshToken= jwtService.generateRefreshToken(user);

        return new String[]{accessToken,refreshToken};
    }

    @Override
    public String refreshToken(String refreshToken) {
        Long userId=jwtService.getUserIdFromToken(refreshToken);
        User user=userService.getUserByUserId(userId);
        return jwtService.generateAccessToken(user);
    }
}
