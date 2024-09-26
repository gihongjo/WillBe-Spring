package com.aidis.teama.user.db;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GoogleUserRepository extends JpaRepository<GoogleUserEntity, Long> {
    Optional<GoogleUserEntity> findByEmail(String email);

}
