package dev.bego.laika.calendar.calendar_exceptions;

public class CalendarNotFoundException extends CalendarException {
    public CalendarNotFoundException(String message) {
        super(message);
    }

    public CalendarNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
