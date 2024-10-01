package com.aidis.teama.user.db;


import com.aidis.teama.student.db.StudentEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "googleUserTable")
@ToString(exclude = "studentEntity") // StudentEntity 목록 참조 제외
public class GoogleUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;
    private String email;
    private String userName;
    private LocalDateTime createdAt;

    @OneToMany(fetch = FetchType.EAGER,mappedBy = "googleUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StudentEntity> students;


}
