package com.task.demo2.utils;

public enum ThreadStatus {
    WAITING("대기"),
    APPROVED("승인"),
    REJECTED("반려");

    private final String status;

    ThreadStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
