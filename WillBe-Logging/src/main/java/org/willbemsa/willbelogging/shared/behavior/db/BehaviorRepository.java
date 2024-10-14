package org.willbemsa.willbelogging.shared.behavior.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.willbemsa.willbelogging.shared.student.db.StudentEntity;

import java.util.List;

public interface BehaviorRepository extends JpaRepository<BehaviorEntity,Long> {

     public List<BehaviorEntity> findAllByStudentEntity(StudentEntity studentEntity);
     public List<BehaviorEntity> findAllByStudentEntityAndAndStatus(StudentEntity studentEntity, String status);



}
