package com.aidis.teama.user.db;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "userTable")
@ToString
public class UserEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String userName;
    private String password;
    private LocalDateTime createdAt;

//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @Builder.Default
//
}
