package com.task.demo2.domain;

import com.task.demo2.utils.Role;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Email
    @Column(length = 75, nullable = false)
    private String email;

    private String name;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @Column(nullable = false)
    @Builder.Default
    private List<UserThread> userThreadList = new ArrayList<>();
}
