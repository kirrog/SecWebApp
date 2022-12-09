package ru.itmo.demoproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.demoproject.model.entity.DocumentEntity;

import java.util.UUID;

public interface DocumentRepository extends JpaRepository<DocumentEntity, UUID> {

}
