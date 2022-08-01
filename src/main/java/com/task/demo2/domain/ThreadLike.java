package com.task.demo2.domain;

import lombok.*;

import javax.persistence.*;

@Table(name = "thread_like")
@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ThreadLike extends TimeEntity {
    @Column(name = "thread_like_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long threadId;

    private Long userId;
}
