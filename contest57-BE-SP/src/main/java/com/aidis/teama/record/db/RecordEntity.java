package com.aidis.teama.record.db;

import com.aidis.teama.behavior.db.BehaviorEntity;
import com.aidis.teama.student.db.StudentEntity;
import com.aidis.teama.user.db.GoogleUserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
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
    @JoinColumn(name = "google_user_id") // 외래 키
    private GoogleUserEntity googleUserEntity;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "behavior_id") // 외래 키
    private BehaviorEntity behaviorEntity;

}
