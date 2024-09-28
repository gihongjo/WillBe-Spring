package com.aidis.teama.behavior.db;

import com.aidis.teama.student.db.StudentEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "behaviorTable")
@ToString
public class BehaviorEntity {


    @Id
    private Long id;
    private String behaviorType;
    private String behaviorName;
    private String recordType;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id") // 외래 키
    private StudentEntity studentEntity;

}
