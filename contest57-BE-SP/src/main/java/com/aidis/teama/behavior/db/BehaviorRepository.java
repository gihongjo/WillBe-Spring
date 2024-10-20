package com.aidis.teama.behavior.db;

import com.aidis.teama.student.db.StudentEntity;
import com.aidis.teama.user.db.GoogleUserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BehaviorRepository extends JpaRepository<BehaviorEntity,Long> {

     public List<BehaviorEntity> findAllByStudentEntity(StudentEntity studentEntity);
     public List<BehaviorEntity> findAllByStudentEntityAndAndStatus(StudentEntity studentEntity, String status);

     @Modifying
     @Transactional
     @Query("UPDATE behaviorTable b SET b.status = :status WHERE b.id = :behaviorId")
     int updateStatusByBehaviorId(@Param("behaviorId") Long behaviorId, @Param("status") String status);



}
