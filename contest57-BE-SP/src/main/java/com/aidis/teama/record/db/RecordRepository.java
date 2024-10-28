package com.aidis.teama.record.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.time.LocalDateTime;
import java.util.List;

public interface RecordRepository extends JpaRepository<RecordEntity,Long> {

    @Query("SELECT r FROM recordTable r JOIN r.behaviorEntity b JOIN b.studentEntity s JOIN s.googleUser g "
            + "WHERE g.id = :googleUserId AND r.time >= CURRENT_DATE "
            + "ORDER BY r.time DESC")
    List<RecordEntity> findTodayRecordEntitiesByGoogleUserId(@Param("googleUserId") Long googleUserId);


    @Query("SELECT r FROM recordTable r JOIN r.behaviorEntity b JOIN b.studentEntity s JOIN s.googleUser g "
            + "WHERE g.id = :googleUserId AND b.id = :behaviorId AND b.status = 'recording' "
            + "AND r.time BETWEEN :startOfDay AND :endOfDay")
    List<RecordEntity> findRecordingRecordsByGoogleUserIdAndBehaviorIdAndDate(
            @Param("googleUserId") Long googleUserId,
            @Param("behaviorId") Long behaviorId,
            @Param("startOfDay") LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay);



    void deleteById(Long id);



    @Query("SELECT r FROM recordTable r JOIN r.behaviorEntity b JOIN b.studentEntity s JOIN s.googleUser g "
            + "WHERE g.id = :googleUserId AND b.id = :behaviorId AND b.status = 'recording' "
            + "AND r.time BETWEEN :startDate AND :endDate")
    List<RecordEntity> findRecordingRecordsByGoogleUserIdAndBehaviorIdBetweenDates(
            @Param("googleUserId") Long googleUserId,
            @Param("behaviorId") Long behaviorId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);

    List<RecordEntity> findAllByBehaviorEntityIdOrderByTimeDesc(Long behaviorEntityId);


}
