package org.willbemsa.willbelogging.record.db;

import jakarta.persistence.*;
import lombok.*;
import org.willbemsa.willbelogging.shared.behavior.db.BehaviorEntity;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "recordTable")
@ToString(exclude = "behaviorEntity")
public class RecordEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime time;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "behavior_id") // 외래 키
    private BehaviorEntity behaviorEntity;

}
