    package com.aidis.teama.behavior.db;

    import com.aidis.teama.notes.db.DailyNoteEntity;
    import com.aidis.teama.student.db.StudentEntity;
    import jakarta.persistence.*;
    import lombok.*;

    import java.util.List;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Entity(name = "behaviorTable")
    @ToString(exclude = "studentEntity")
    public class BehaviorEntity {


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String behaviorType;
        private String behaviorName;
        private String recordType;
        private String status;

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "student_id") // 외래 키
        private StudentEntity studentEntity;


        @OneToMany(mappedBy = "behaviorEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        private List<DailyNoteEntity> dailyNoteEntities;


    }
