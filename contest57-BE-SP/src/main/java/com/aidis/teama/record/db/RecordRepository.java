package com.aidis.teama.record.db;

import com.aidis.teama.behavior.db.BehaviorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecordRepository extends JpaRepository<RecordEntity,Long> {

    public List<RecordEntity> findAllByBehaviorEntity(BehaviorEntity behaviorEntity);

}
