package com.aidis.teama.student.db;

import com.aidis.teama.user.db.GoogleUserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentRepository extends JpaRepository<StudentEntity,Long> {

    public List<StudentEntity> findByGoogleUserOrderByCreatedAtDesc(GoogleUserEntity googleUserEntity);


    public List<StudentEntity> findAllByGoogleUser(GoogleUserEntity googleUserEntity);


}
