package org.willbemsa.willbelogging.shared.student.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.willbemsa.willbelogging.shared.user.db.GoogleUserEntity;

import java.util.List;

public interface StudentRepository extends JpaRepository<StudentEntity,Long> {

    public List<StudentEntity> findByGoogleUserOrderByCreatedAtDesc(GoogleUserEntity googleUserEntity);

    public List<StudentEntity> findAllByGoogleUser(GoogleUserEntity googleUserEntity);


}
