package com.task.demo2.dto.req;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@NoArgsConstructor
public class ThreadCreateReqDto {
    @NotBlank(message = "Thread Title is required.")
    private String title;
//    private String thumbnailUrl;
//    private String coverImage;
    private String content;
}
