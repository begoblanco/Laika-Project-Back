package dev.bego.laika.calendar_events;

import lombok.*;

import java.time.LocalDateTime;

import dev.bego.laika.users.UserDto;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CalendarEventDto {
    private Long id;
    private String title;
    private LocalDateTime start_date;
    private UserDto user;
}
