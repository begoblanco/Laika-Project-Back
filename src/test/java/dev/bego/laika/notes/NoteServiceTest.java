package dev.bego.laika.notes;

import dev.bego.laika.users.User;
import dev.bego.laika.users.UserDto;
import dev.bego.laika.users.UserRepository;
import dev.bego.laika.users.user_exceptions.UserNotFoundException;
import dev.bego.laika.notes.notes_exceptions.NoteNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class NoteServiceTest {

    @Mock
    private NoteRepository noteRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private NoteService noteService;

    private Note note;
    private User user;
    private CreateNoteDto createNoteDto;
    private NoteDto noteDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Configuración inicial
        user = new User();
        user.setId(1L);
        user.setUsername("testuser");

        note = Note.builder()
                .id(1L)
                .title("Test Title")
                .content("Test Content")
                .user(user)
                .build();

        createNoteDto = new CreateNoteDto();
        createNoteDto.setTitle("Test Title");
        createNoteDto.setContent("Test Content");

        noteDto = NoteDto.builder()
                .id(1L)
                .title("Test Title")
                .content("Test Content")
                .user(UserDto.builder().id(1L).username("testuser").build())
                .build();
    }

    @Test
    void testGetAllNotesByUserId() {
        // Arrange
        when(noteRepository.findByUserId(user.getId())).thenReturn(Arrays.asList(note));

        // Act
        List<NoteDto> notes = noteService.getAllNotesByUserId(user.getId());

        // Assert
        assertEquals(1, notes.size());
        assertEquals(note.getId(), notes.get(0).getId());
        verify(noteRepository, times(1)).findByUserId(user.getId());
    }

    @Test
    void testGetNoteById_NoteExists() {
        // Arrange
        when(noteRepository.findById(note.getId())).thenReturn(Optional.of(note));

        // Act
        Optional<NoteDto> foundNote = noteService.getNoteById(note.getId());

        // Assert
        assertTrue(foundNote.isPresent());
        assertEquals(note.getId(), foundNote.get().getId());
        verify(noteRepository, times(1)).findById(note.getId());
    }

   
    @Test
    void testCreateNoteForUser_UserExists() {
        // Arrange
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(noteRepository.save(any(Note.class))).thenReturn(note);

        // Act
        NoteDto createdNote = noteService.createNoteForUser(user.getId(), createNoteDto);

        // Assert
        assertEquals(note.getId(), createdNote.getId());
        verify(userRepository, times(1)).findById(user.getId());
        verify(noteRepository, times(1)).save(any(Note.class));
    }

    @Test
    void testCreateNoteForUser_UserNotFound() {
        // Arrange
        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> noteService.createNoteForUser(user.getId(), createNoteDto));
        verify(userRepository, times(1)).findById(user.getId());
    }

    @Test
    void testUpdateNote_NoteExists() {
        // Arrange
        when(noteRepository.findById(note.getId())).thenReturn(Optional.of(note));
        when(noteRepository.save(any(Note.class))).thenReturn(note);

        // Act
        NoteDto updatedNote = noteService.updateNote(note.getId(), noteDto);

        // Assert
        assertEquals(note.getId(), updatedNote.getId());
        verify(noteRepository, times(1)).findById(note.getId());
        verify(noteRepository, times(1)).save(any(Note.class));
    }

    @Test
    void testUpdateNote_NoteNotFound() {
        // Arrange
        when(noteRepository.findById(note.getId())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NoteNotFoundException.class, () -> noteService.updateNote(note.getId(), noteDto));
        verify(noteRepository, times(1)).findById(note.getId());
    }

    @Test
    void testDeleteNoteById() {
        // Act
        noteService.deleteNoteById(note.getId());

        // Assert
        verify(noteRepository, times(1)).deleteById(note.getId());
    }
}
