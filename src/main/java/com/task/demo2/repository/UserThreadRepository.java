package com.task.demo2.repository;

import com.task.demo2.domain.UserThread;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserThreadRepository extends JpaRepository<UserThread, Long> {
}
