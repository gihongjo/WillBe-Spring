package com.aidis.teama.record.db;

import com.aidis.teama.behavior.db.BehaviorEntity;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "recordTable")
@ToString
public class RecordEntity {
    @Id
    private Long id;

    private Timestamp time;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "behavior_id") // 외래 키
    private BehaviorEntity behaviorEntity;

}
