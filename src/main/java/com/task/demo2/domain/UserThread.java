package com.task.demo2.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "user_thread")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserThread extends TimeEntity {

    @Column(name = "user_thread_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "thread_id")
    private Thread thread;

    @Builder
    public UserThread(User user, Thread thread) {
        this.user = user;
        this.thread = thread;
        user.getUserThreadList().add(this);
        thread.getUserThreadList().add(this);
    }
}
