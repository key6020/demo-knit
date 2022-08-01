package com.task.demo2.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    ADMIN("ADMIN", "어드민"),
    USER("USER", "사용자");
    private final String role;
    private final String description;
}
