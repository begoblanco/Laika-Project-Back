package dev.bego.laika.notes;

import dev.bego.laika.users.UserDto;
import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoteDto {
    
    private Long id;
    private String title;
    private String content;
    private UserDto user;
}
