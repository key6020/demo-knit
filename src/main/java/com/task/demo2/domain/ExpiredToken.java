package com.task.demo2.domain;

import lombok.*;

import javax.persistence.*;

@Table(name = "expired_token")
@Entity
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExpiredToken extends TimeEntity {

    @Column(name = "expired_token_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(name = "access_token", columnDefinition = "LONGTEXT")
    private String accessToken;
    @Column(name = "refresh_token", columnDefinition = "LONGTEXT")
    private String refreshToken;
}
