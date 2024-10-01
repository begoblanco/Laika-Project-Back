package dev.bego.laika.auth;


import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import dev.bego.laika.users.User;
import dev.bego.laika.users.UserRepository;

import java.util.Optional;

public class AuthenticationServiceTest {

    @InjectMocks
    private AuthenticationService authenticationService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAuthenticate_Success() {
       
        String username = "testuser";
        String password = "password";
        LoginDto loginDto = new LoginDto(username, password);
        
        User mockUser = new User(username, "encodedPassword");
        
        when(passwordEncoder.encode(password)).thenReturn("encodedPassword");
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(mockUser));
        
      
        User authenticatedUser = authenticationService.authenticate(loginDto);
        
        
        assertNotNull(authenticatedUser);
        assertEquals(username, authenticatedUser.getUsername());
    }
    
    @Test
    public void testAuthenticate_UserNotFound() {
        
        String username = "unknownuser";
        String password = "password";
        LoginDto loginDto = new LoginDto(username, password);
        
        when(passwordEncoder.encode(password)).thenReturn("encodedPassword");
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        
        
        assertThrows(RuntimeException.class, () -> {
            authenticationService.authenticate(loginDto);
        });
    }
}



