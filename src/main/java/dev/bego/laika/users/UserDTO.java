package dev.bego.laika.users;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    
    private Long id;
    private String username;
}
