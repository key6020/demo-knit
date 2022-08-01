package com.task.demo2.dto.res;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class ThreadResDto {
    private Long id;
    private String title;
    private String content;
    //    private String thumbnailUrl;
    private Integer likeCount;
    private LocalDateTime createdDate;
}
