package com.example.demo.registration.token;

import com.example.demo.appuser.AppUser;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ConfirmationTokenRepository
        extends JpaRepository<ConfirmationToken, Long> {

    Optional<ConfirmationToken> findByToken(String token);

    @Transactional
    @Modifying
    @Query("UPDATE ConfirmationToken c " +
            "SET c.confirmedAt = :confirmedAt " +
            "WHERE c.token = :token")
    int updateConfirmedAt(@Param("token") String token,
                          @Param("confirmedAt") LocalDateTime confirmedAt);

    @Transactional
    @Modifying
    @Query("DELETE FROM ConfirmationToken t WHERE t.appUser = :appUser")
    void deleteByAppUser(@Param("appUser") AppUser appUser);

    @Transactional
    @Modifying
    @Query("DELETE FROM ConfirmationToken t WHERE t.expiresAt < :currentTime")
    void deleteExpiredTokens(@Param("currentTime") LocalDateTime currentTime);
}