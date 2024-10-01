package com.aidis.teama.record.db;

import com.aidis.teama.behavior.db.BehaviorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



import java.util.List;

public interface RecordRepository extends JpaRepository<RecordEntity,Long> {

    @Query("SELECT r FROM recordTable r JOIN r.behaviorEntity b JOIN b.studentEntity s JOIN s.googleUser g "
            + "WHERE g.id = :googleUserId AND b.status = 'recording' AND DATE(r.time) = CURRENT_DATE")
    List<RecordEntity> findTodayRegisteredRecordsByGoogleUserId(@Param("googleUserId") Long googleUserId);



}
