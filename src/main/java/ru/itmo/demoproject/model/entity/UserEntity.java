package ru.itmo.demoproject.model.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Table(name = "user")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Builder
@AllArgsConstructor
public class UserEntity {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "code", nullable = false)
    private String code;
}
