package dev.bego.laika.calendar_events;

import java.time.LocalDateTime;

import dev.bego.laika.users.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventDto {
    private Long id;
    private String title;
    private LocalDateTime start_date;
    private UserDto user;
}
