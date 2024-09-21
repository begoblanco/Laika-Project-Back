package dev.bego.laika.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.bego.laika.users.User;
import dev.bego.laika.users.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping(path = "${api-endpoint}/register")
public class RegisterController {

     private final UserService service;

    public RegisterController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> register(@Valid @RequestBody RegisterDto newUser) {
        if(!service.findByUsername(newUser.getUsername()).isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "User already exists"));
        }
        User user = service.save(newUser);

        Map<String, String> json = new HashMap<>();
        json.put("message", "Register successful");
        json.put("username", user.getUsername());

        return ResponseEntity.status(HttpStatus.CREATED).body(json);
    }
}
