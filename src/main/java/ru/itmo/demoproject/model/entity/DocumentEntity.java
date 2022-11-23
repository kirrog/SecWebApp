package ru.itmo.demoproject.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;
@Table(name = "document")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Builder
@AllArgsConstructor
public class DocumentEntity {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "token", nullable = false)
    private String token;
    @ManyToOne
    @JoinColumn(name = "directory_id", referencedColumnName = "id", nullable = false)
    private DirectoryEntity parent;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserEntity owner;
}
