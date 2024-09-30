package com.aidis.teama.student.db;

import com.aidis.teama.user.db.GoogleUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<StudentEntity,Long> {

    public List<StudentEntity> findByGoogleUserOrderByCreatedAtDesc(GoogleUserEntity googleUserEntity);

    public List<StudentEntity> findAllByGoogleUser(GoogleUserEntity googleUserEntity);


}
