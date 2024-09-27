package dev.bego.laika.auth;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {
    private String username;
    private String password;
}

