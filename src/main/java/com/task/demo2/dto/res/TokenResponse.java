package com.task.demo2.dto.res;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TokenResponse {
    private String accessToken;
    private String refreshToken;
}
