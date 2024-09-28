package com.aidis.teama.student.db;

import com.aidis.teama.user.db.GoogleUserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "studentTable")
@ToString(exclude = "googleUser") // GoogleUserEntity 참조 제외
public class StudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String student_name;
    private LocalDate birthday;
    private String expressionLevel;
    private String status;
    private Timestamp createdAt;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "google_user_id") // 외래 키
    private GoogleUserEntity googleUser;



}