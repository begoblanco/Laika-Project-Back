package dev.bego.laika.notes;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import dev.bego.laika.users.User;

@Controller
@RequestMapping(path = "${api-endpoint}/notes")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @GetMapping("")
    public ResponseEntity<List<NoteDto>> getAllNotesByUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        List<NoteDto> notes = noteService.getAllNotesByUserId(currentUser.getId());
        return ResponseEntity.ok(notes);
    }

    @PostMapping("")
    public ResponseEntity<NoteDto> createNote(@RequestBody CreateNoteDto noteDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        NoteDto createdNote = noteService.createNoteForUser(currentUser.getId(), noteDto);
        return ResponseEntity.ok(createdNote);
    }

    @GetMapping("/{noteId}")
    public ResponseEntity<NoteDto> getNoteById(@PathVariable Long noteId) {
        return noteService.getNoteById(noteId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{noteId}")
    public ResponseEntity<NoteDto> updateNote(@PathVariable Long noteId, @RequestBody NoteDto noteDetails) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        Optional<NoteDto> note = noteService.getNoteById(noteId);
        if (!note.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        if (note.get().getUser().getId() != currentUser.getId()) {
            return ResponseEntity.badRequest().build();
        }
        NoteDto updatedNote = noteService.updateNote(noteId, noteDetails);
        return ResponseEntity.ok(updatedNote);
    }

    @DeleteMapping("/{noteId}")
    public ResponseEntity<Void> deleteNoteById(@PathVariable Long noteId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        Optional<NoteDto> note = noteService.getNoteById(noteId);
        if (!note.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        if (note.get().getUser().getId() != currentUser.getId()) {
            return ResponseEntity.badRequest().build();
        }
        noteService.deleteNoteById(noteId);
        return ResponseEntity.noContent().build();
    }
}
