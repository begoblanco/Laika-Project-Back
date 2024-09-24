package dev.bego.laika.calendar_events.calendar_exceptions;

public class CalendarEventException extends RuntimeException {

    public CalendarEventException(String message) {  
        super(message);  
    }

    public CalendarEventException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
