package dev.bego.laika.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.bego.laika.users.User;
import dev.bego.laika.users.UserService;
import jakarta.validation.Valid;

@RequestMapping("${api-endpoint}/auth")
@RestController
public class AuthenticationController {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private UserService userService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginDto loginDto) {
        User authenticatedUser = authenticationService.authenticate(loginDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse(authenticatedUser.getId(), jwtToken,
                jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);

    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@Valid @RequestBody RegisterDto newUser) {
        if (!userService.findByUsername(newUser.getUsername()).isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "User already exists"));
        }
        User user = authenticationService.signup(newUser);

        Map<String, String> json = new HashMap<>();
        json.put("message", "Register successful");
        json.put("username", user.getUsername());

        return ResponseEntity.status(HttpStatus.CREATED).body(json);
    }
}