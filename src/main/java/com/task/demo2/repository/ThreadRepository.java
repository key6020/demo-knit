package com.task.demo2.repository;

import com.task.demo2.domain.Thread;
import com.task.demo2.utils.ThreadStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ThreadRepository extends JpaRepository<Thread, Long> {
    List<Thread> findAllByStatusOrderByModifiedDateDesc(Pageable pageable, ThreadStatus status);

    Thread findByIdAndStatus(Long id, ThreadStatus status);

    boolean existsByIdAndStatus(Long id, ThreadStatus status);
}
