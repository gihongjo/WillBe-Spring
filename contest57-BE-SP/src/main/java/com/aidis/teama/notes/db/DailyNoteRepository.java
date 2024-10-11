package com.aidis.teama.notes.db;

import com.aidis.teama.behavior.db.BehaviorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface DailyNoteRepository extends JpaRepository<DailyNoteEntity,Long> {

    @Query("SELECT d FROM DailyTable d WHERE d.behaviorEntity = :behaviorEntity AND d.date = :date")
    DailyNoteEntity findByBehaviorEntityAndDate(@Param("behaviorEntity") BehaviorEntity behaviorEntity,
                                                      @Param("date") LocalDate date);


}
