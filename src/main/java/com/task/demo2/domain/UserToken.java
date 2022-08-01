package com.task.demo2.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_token")
@Entity
public class UserToken extends TimeEntity {
    @Column(name = "user_token_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private Long userId;

    @Column(name = "refresh_token", columnDefinition = "LONGTEXT")
    private String refreshToken;

    @Builder
    public UserToken(Long userId, String refreshToken) {
        this.userId = userId;
        this.refreshToken = refreshToken;
    }
}
