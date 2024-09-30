package com.aidis.teama.behavior.db;

import com.aidis.teama.student.db.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BehaviorRepository extends JpaRepository<BehaviorEntity,Long> {

     public List<BehaviorEntity> findAllByStudentEntity(StudentEntity studentEntity);
     public List<BehaviorEntity> findAllByStudentEntityAndAndStatus(StudentEntity studentEntity, String status);




}
