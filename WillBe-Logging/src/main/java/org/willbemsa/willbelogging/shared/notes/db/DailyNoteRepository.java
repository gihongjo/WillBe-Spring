package org.willbemsa.willbelogging.shared.notes.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.willbemsa.willbelogging.shared.behavior.db.BehaviorEntity;

import java.time.LocalDate;

public interface DailyNoteRepository extends JpaRepository<DailyNoteEntity,Long> {

    @Query("SELECT d FROM DailyTable d WHERE d.behaviorEntity = :behaviorEntity AND d.date = :date")
    DailyNoteEntity findByBehaviorEntityAndDate(@Param("behaviorEntity") BehaviorEntity behaviorEntity,
                                                      @Param("date") LocalDate date);


}
