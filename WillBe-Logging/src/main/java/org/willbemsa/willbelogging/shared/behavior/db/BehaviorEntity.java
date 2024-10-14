    package org.willbemsa.willbelogging.shared.behavior.db;


    import jakarta.persistence.*;
    import lombok.*;
    import org.willbemsa.willbelogging.shared.notes.db.DailyNoteEntity;
    import org.willbemsa.willbelogging.shared.student.db.StudentEntity;

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
