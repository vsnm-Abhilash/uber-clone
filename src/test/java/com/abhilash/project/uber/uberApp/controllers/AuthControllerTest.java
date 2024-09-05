package com.abhilash.project.uber.uberApp.controllers;

import com.abhilash.project.uber.uberApp.dto.SignUpDTO;
import com.abhilash.project.uber.uberApp.entities.User;
import com.abhilash.project.uber.uberApp.entities.enums.Role;
import com.abhilash.project.uber.uberApp.repositories.UserRepository;
import com.abhilash.project.uber.uberApp.security.JWTService;
import com.abhilash.project.uber.uberApp.security.UserService;
import com.abhilash.project.uber.uberApp.services.DriverService;
import com.abhilash.project.uber.uberApp.services.RiderService;
import com.abhilash.project.uber.uberApp.services.WalletService;
import com.abhilash.project.uber.uberApp.services.impl.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Test
    void signUp() {
    }

    @Test
    void testLogin_withSuccess() {
        //arrange

        //act

        //assert
    }

    @Test
    void onBoardNewDriver() {
    }

    @Test
    void refresh() {
    }
}