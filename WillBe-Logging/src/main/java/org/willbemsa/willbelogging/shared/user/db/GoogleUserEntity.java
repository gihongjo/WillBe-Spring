package org.willbemsa.willbelogging.shared.user.db;


import jakarta.persistence.*;
import lombok.*;
import org.willbemsa.willbelogging.shared.student.db.StudentEntity;

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
