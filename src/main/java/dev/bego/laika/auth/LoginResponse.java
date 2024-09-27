package dev.bego.laika.auth;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private long userId;
    private String token;
    private long expiresIn;
}
