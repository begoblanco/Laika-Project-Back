package dev.bego.laika.notes;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateNoteDto {
    private String title;
    private String content;
}
