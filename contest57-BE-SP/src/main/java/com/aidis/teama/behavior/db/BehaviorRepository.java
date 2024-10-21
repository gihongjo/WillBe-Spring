package com.aidis.teama.behavior.db;

import com.aidis.teama.student.db.StudentEntity;
import com.aidis.teama.user.db.GoogleUserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface BehaviorRepository extends JpaRepository<BehaviorEntity,Long> {

     public List<BehaviorEntity> findAllByStudentEntity(StudentEntity studentEntity);
     public List<BehaviorEntity> findAllByStudentEntityAndStatus(StudentEntity studentEntity, String status);

     @Modifying
     @Transactional
     @Query("UPDATE behaviorTable b SET b.status = :status, b." +
             "overDescription = :overDescription, " +
             "b.overMeaning = :overMeaning, " +
             "b.overMeasures = :overMeasures " +
             "WHERE b.id = :behaviorId")
     int updateStatusByBehaviorId(
             @Param("behaviorId") Long behaviorId,
             @Param("status") String status,
             @Param("overDescription") String overDescription,
             @Param("overMeaning") String overMeaning,
             @Param("overMeasures") String overMeasures
     );




}
