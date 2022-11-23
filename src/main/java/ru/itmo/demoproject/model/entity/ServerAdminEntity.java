package ru.itmo.demoproject.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;
@Table(name = "admin")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Builder
@AllArgsConstructor
public class ServerAdminEntity {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserEntity userEntity;
}
