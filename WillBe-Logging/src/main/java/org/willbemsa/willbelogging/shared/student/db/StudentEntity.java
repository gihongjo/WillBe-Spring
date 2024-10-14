package org.willbemsa.willbelogging.shared.student.db;


import jakarta.persistence.*;
import lombok.*;
import org.willbemsa.willbelogging.shared.behavior.db.BehaviorEntity;
import org.willbemsa.willbelogging.shared.user.db.GoogleUserEntity;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "studentTable")
@ToString(exclude = "googleUserEntity") // GoogleUserEntity 참조 제외
public class StudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String student_name;
    private LocalDate birthday;
    private String expressionLevel;
    private String status;
    private Timestamp createdAt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "google_user_id") // 외래 키
    private GoogleUserEntity googleUser;


    @OneToMany(mappedBy = "studentEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BehaviorEntity> behaviors;

}
