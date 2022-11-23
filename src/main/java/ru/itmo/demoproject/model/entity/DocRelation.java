package ru.itmo.demoproject.model.entity;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Table(name = "docrelaltion")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Builder
@AllArgsConstructor
public class DocRelation {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "par_id", referencedColumnName = "id", nullable = false)
    private DocumentEntity par_id;
    @ManyToOne
    @JoinColumn(name = "chi_id", referencedColumnName = "id", nullable = false)
    private DocumentEntity chi_id;
}
