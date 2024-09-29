package dev.bego.laika.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InformedMessageDto {
    private Long user_id;
    private String user_name;
    private String message;
    private String current_date;
}
