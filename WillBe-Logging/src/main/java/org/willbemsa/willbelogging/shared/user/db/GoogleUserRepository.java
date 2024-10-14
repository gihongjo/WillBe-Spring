package org.willbemsa.willbelogging.shared.user.db;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface GoogleUserRepository extends JpaRepository<GoogleUserEntity, Long> {
    Optional<GoogleUserEntity> findByEmail(String email);

    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END " +
            "FROM googleUserTable g " +
            "JOIN studentTable s ON g.id = s.googleUser.id " +  // googleUserEntity의 ID와 studentEntity의 googleUserId를 매핑
            "WHERE g = :googleUserEntity")
    boolean findByEmailAndCheckStudentExist(@Param("googleUserEntity") GoogleUserEntity googleUserEntity);

}
