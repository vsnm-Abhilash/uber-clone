package com.abhilash.project.uber.uberApp.services.impl;

import com.abhilash.project.uber.uberApp.dto.LoginRequestDTO;
import com.abhilash.project.uber.uberApp.dto.SignUpDTO;
import com.abhilash.project.uber.uberApp.dto.UserDTO;
import com.abhilash.project.uber.uberApp.entities.User;
import com.abhilash.project.uber.uberApp.entities.enums.Role;
import com.abhilash.project.uber.uberApp.repositories.UserRepository;
import com.abhilash.project.uber.uberApp.security.JWTService;
import com.abhilash.project.uber.uberApp.security.UserService;
import com.abhilash.project.uber.uberApp.services.DriverService;
import com.abhilash.project.uber.uberApp.services.RiderService;
import com.abhilash.project.uber.uberApp.services.WalletService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.extractProperty;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Spy
    private ModelMapper mapper;
    @Mock
    private UserRepository userRepository;
    @Mock
    private RiderService riderService;
    @Mock
    private WalletService walletService;
    @Mock
    private DriverService driverService;
    @Spy
    private PasswordEncoder passwordEncoder;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JWTService jwtService;
    @Mock
    private UserService userService;

    @InjectMocks
    private AuthServiceImpl authService;


    private User user;


    @BeforeEach
    void setUp(){
        user=new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setPassword("dummy");
        user.setRoles(Set.of(Role.RIDER));

    }

    @Test
    void testSignUp_withSuccess() {
        //arrange
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);

        //act
        SignUpDTO signUpDTO=new SignUpDTO();
        signUpDTO.setEmail("test@example.com");
        signUpDTO.setPassword("dummy");
        UserDTO userDTO=authService.signUp(signUpDTO);

        //assert
        assertThat(userDTO).isNotNull();
        assertThat(userDTO.getEmail()).isEqualTo(signUpDTO.getEmail());

        verify(riderService).createNewRider(any(User.class));
        verify(walletService).createNewWallet(any(User.class));
    }

    @Test
    void onBoardNewDriver() {
    }

    @Test
    void testLogin_withSuccess() {
        //arrange
        Authentication authentication= mock(Authentication.class);
        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(user);
        when(jwtService.generateAccessToken(any(User.class))).thenReturn("accessToken");
        when(jwtService.generateRefreshToken(any(User.class))).thenReturn("refreshToken");

        //act
        LoginRequestDTO loginRequestDTO=new LoginRequestDTO();
        loginRequestDTO.setEmail(user.getEmail());
        loginRequestDTO.setPassword(user.getPassword());
        String tokens[]= authService.login(loginRequestDTO);

        //assert
        assertThat(tokens).hasSize(2);
        assertThat(tokens[0]).isEqualTo("accessToken");
        assertThat(tokens[1]).isEqualTo("refreshToken");

    }
    @Test
    void refreshToken() {
    }
}