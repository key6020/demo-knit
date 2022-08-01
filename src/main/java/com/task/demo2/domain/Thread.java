package com.task.demo2.domain;

import com.task.demo2.utils.ThreadStatus;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Thread extends TimeEntity {
    @Column(name = "thread_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "thread_title", updatable = false)
    private String threadTitle;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(name = "cover_image")
    private String coverImage;

    private Long viewCount;

    private Long likeCount;

    @Enumerated(EnumType.STRING)
    private ThreadStatus status;

    @OneToMany(mappedBy = "thread", cascade = CascadeType.ALL)
    @Column(nullable = false)
    @Builder.Default
    private List<UserThread> userThreadList = new ArrayList<>();

    public void updateContent(String content) {
        this.content = content;
    }
}
