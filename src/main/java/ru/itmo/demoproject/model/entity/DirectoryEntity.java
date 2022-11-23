package ru.itmo.demoproject.model.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;
@Table(name = "directory")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Builder
@AllArgsConstructor
public class DirectoryEntity {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "token", nullable = false)
    private String token;
}
