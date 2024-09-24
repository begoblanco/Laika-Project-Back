package dev.bego.laika.notes;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long>{
    List<Note> findByUserId(Long userId);
}

