package com.abhilash.project.uber.uberApp.services;

import com.abhilash.project.uber.uberApp.dto.*;

public interface AuthService {
    UserDTO signUp(SignUpDTO signUpDTO);
    DriverDTO onBoardNewDriver(Long userId,String vehicleId);
    String[] login(LoginRequestDTO loginRequestDTO);

    String refreshToken(String refreshToken);
}
