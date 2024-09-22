package dev.bego.laika.notes;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.bego.laika.users.*;

@Service
public class NoteService {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    @Autowired
    public NoteService(NoteRepository noteRepository, UserRepository userRepository) {
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
    }
    
    public List<NoteDto> getAllNotesByUserId(Long userId) {
        return noteRepository.findByUserId(userId).stream()
                .map(this::toNoteDto)  
                .collect(Collectors.toList());
    }

    public Optional<NoteDto> getNoteById(Long noteId) {
        return noteRepository.findById(noteId)
                .map(this::toNoteDto);  
    }

    public NoteDto createNoteForUser(Long userId, NoteDto noteDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Note note = toNoteEntity(noteDto, user);  
        Note savedNote = noteRepository.save(note);
        return toNoteDto(savedNote);  
    }

    public NoteDto updateNote(Long noteId, NoteDto noteDetails) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new NoteNotFoundException("Note not found"));

        note.setTitle(noteDetails.getTitle());
        note.setContent(noteDetails.getContent());

        Note updatedNote = noteRepository.save(note);
        return toNoteDto(updatedNote);  
    }

    public void deleteNoteById(Long noteId) {
        noteRepository.deleteById(noteId);
    }

    private NoteDto toNoteDto(Note note) {
        UserDto userDto = UserDto.builder()
                .id(note.getUser().getId())
                .username(note.getUser().getUsername())
                .build();

        return NoteDto.builder()
                .id(note.getId())
                .title(note.getTitle())
                .content(note.getContent())
                .user(userDto)
                .build();
    }


    private Note toNoteEntity(NoteDto noteDto, User user) {
        return Note.builder()
                .id(noteDto.getId())
                .title(noteDto.getTitle())
                .content(noteDto.getContent())
                .user(user)
                .build();
    }

}
