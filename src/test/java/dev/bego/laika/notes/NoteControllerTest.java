package dev.bego.laika.notes;

import dev.bego.laika.users.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.List;

class NoteControllerTest {

    @Mock
    private NoteService noteService;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private NoteController noteController;

    private User mockUser;
    private NoteDto mockNote;
    private CreateNoteDto mockCreateNote;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockUser = new User();
        mockUser.setId(1L);
        mockUser.setUsername("testuser");

        mockNote = new NoteDto();
        mockNote.setId(1L);
        mockNote.setTitle("Test Note");

        mockCreateNote = new CreateNoteDto();
        mockCreateNote.setTitle("New Note");

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(mockUser);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void testGetAllNotesByUser_Success() {

        List<NoteDto> notes = Collections.singletonList(mockNote);
        when(noteService.getAllNotesByUserId(mockUser.getId())).thenReturn(notes);

        ResponseEntity<List<NoteDto>> response = noteController.getAllNotesByUser();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(notes.size(), response.getBody().size());

        verify(noteService, times(1)).getAllNotesByUserId(mockUser.getId());
    }

    @Test
    void testCreateNote_Success() {
        when(noteService.createNoteForUser(mockUser.getId(), mockCreateNote)).thenReturn(mockNote);

        ResponseEntity<NoteDto> response = noteController.createNote(mockCreateNote);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockNote.getTitle(), response.getBody().getTitle());

        verify(noteService, times(1)).createNoteForUser(mockUser.getId(), mockCreateNote);
    }

    @Test
    void testGetNoteById_Success() {
        when(noteService.getNoteById(1L)).thenReturn(Optional.of(mockNote));

        ResponseEntity<NoteDto> response = noteController.getNoteById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockNote.getTitle(), response.getBody().getTitle());

        verify(noteService, times(1)).getNoteById(1L);
    }

}
