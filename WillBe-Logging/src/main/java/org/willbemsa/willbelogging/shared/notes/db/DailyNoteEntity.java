package org.willbemsa.willbelogging.shared.notes.db;


import jakarta.persistence.*;
import lombok.*;
import org.willbemsa.willbelogging.shared.behavior.db.BehaviorEntity;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "DailyTable")
@ToString(exclude = "DailyEntity")
public class DailyNoteEntity {
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "behavior_id") // 외래 키
    private BehaviorEntity behaviorEntity;

    private LocalDate date;
    private String preSituation;
    private String postSituation;
    private String remark;




}
