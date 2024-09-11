package com.aidis.teama.student.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "StudentBehaviorTable")
@ToString
public class StudentRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String token;
    private String childName;
    private String behaviorType;
    private String behaviorName;
    private String recordType;
}