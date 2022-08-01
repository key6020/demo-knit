package com.task.demo2.repository;

import com.task.demo2.domain.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserTokenRepository extends JpaRepository<UserToken, Long> {
    List<UserToken> findAllByUserId(Long userId);

    UserToken findTopByRefreshToken(String refreshToken);

    void deleteAllByRefreshToken(String refreshToken);
}
