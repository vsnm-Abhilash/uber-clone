package com.abhilash.project.uber.uberApp.services;

import com.abhilash.project.uber.uberApp.dto.DriverDTO;
import com.abhilash.project.uber.uberApp.dto.SignUpDTO;
import com.abhilash.project.uber.uberApp.dto.UserDTO;

public interface AuthService {
    String login(String username,String password);
    UserDTO signUp(SignUpDTO signUpDTO);
    DriverDTO onBoardNewDriver(Long userId,String vehicleId);
}
