package dev.bego.laika.calendar_events.calendar_exceptions;

public class CalendarEventNotFoundException extends CalendarEventException {
    public CalendarEventNotFoundException(String message) {
        super(message);
    }

    public CalendarEventNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
