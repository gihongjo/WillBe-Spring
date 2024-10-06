package com.aidis.teama.notes.db;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyNoteRepository extends JpaRepository<DailyNoteEntity,Long> {
}
