package ru.itmo.demoproject.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Table(name = "documenttype")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Builder
@AllArgsConstructor
public class DocumentType {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "size", nullable = false)
    private Long size;

}
