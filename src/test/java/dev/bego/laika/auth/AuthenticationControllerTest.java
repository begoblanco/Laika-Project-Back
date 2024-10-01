package dev.bego.laika.auth;

import dev.bego.laika.users.User;
import dev.bego.laika.users.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class AuthenticationControllerTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationService authenticationService;

    @Mock
    private UserService userService;

    @InjectMocks
    private AuthenticationController authenticationController;

    private User mockUser;
    private String mockJwtToken = "mock-jwt-token";
    private long expirationTime = 3600000; 

    @BeforeEach
    void setUp() {
      
        MockitoAnnotations.openMocks(this);

        mockUser = new User();
        mockUser.setId(1L);
        mockUser.setUsername("testuser");
    }

    @Test
    void testAuthenticate_Success() {
        
        LoginDto loginDto = new LoginDto("testuser", "password123");
        when(authenticationService.authenticate(loginDto)).thenReturn(mockUser);
        when(jwtService.generateToken(mockUser)).thenReturn(mockJwtToken);
        when(jwtService.getExpirationTime()).thenReturn(expirationTime);

        ResponseEntity<LoginResponse> response = authenticationController.authenticate(loginDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockUser.getId(), response.getBody().getUserId());
        assertEquals(mockJwtToken, response.getBody().getToken());
    }
}
