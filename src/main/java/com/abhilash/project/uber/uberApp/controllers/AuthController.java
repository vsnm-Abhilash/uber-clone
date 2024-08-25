package com.abhilash.project.uber.uberApp.controllers;

import com.abhilash.project.uber.uberApp.dto.DriverDTO;
import com.abhilash.project.uber.uberApp.dto.SignUpDTO;
import com.abhilash.project.uber.uberApp.dto.UserDTO;
import com.abhilash.project.uber.uberApp.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    @PostMapping("/signup")
    public ResponseEntity<UserDTO> signUp(@RequestBody SignUpDTO signUpDTO){
        return new ResponseEntity<>(authService.signUp(signUpDTO), HttpStatus.CREATED);
    }

    @PostMapping("/onBoardNewDriver/{userId}/{vehicleId}")
    public ResponseEntity<DriverDTO> onBoardNewDriver(@PathVariable Long userId,@PathVariable String vehicleId){
        return new ResponseEntity<>(authService.onBoardNewDriver(userId,vehicleId),HttpStatus.CREATED);
    }
}
