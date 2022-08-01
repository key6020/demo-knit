package com.task.demo2.dto.req;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class SignupReqDto {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String name;
}
