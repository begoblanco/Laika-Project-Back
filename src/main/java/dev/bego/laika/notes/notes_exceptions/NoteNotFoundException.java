package dev.bego.laika.notes.notes_exceptions;

public class NoteNotFoundException extends NoteException {
    
    public NoteNotFoundException(String message) {
        super(message);
    }

    public NoteNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
