package ru.itmo.demoproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.demoproject.model.entity.DocumentType;

import java.util.UUID;

@Repository
public interface DocumentTypeRepository extends JpaRepository<DocumentType, UUID> {
}
