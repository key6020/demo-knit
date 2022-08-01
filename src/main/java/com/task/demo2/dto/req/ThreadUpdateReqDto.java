package com.task.demo2.dto.req;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@NoArgsConstructor
public class ThreadUpdateReqDto {
    //    private String thumbnailUrl;
//    private String coverImage;
    @NotBlank
    private String content;
}
