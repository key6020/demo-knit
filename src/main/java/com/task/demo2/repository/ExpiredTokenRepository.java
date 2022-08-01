package com.task.demo2.repository;

import com.task.demo2.domain.ExpiredToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpiredTokenRepository extends JpaRepository<ExpiredToken,Long> {
    boolean existsByAccessToken(String accessToken);
    void deleteAllByRefreshToken(String refreshToken);
}
