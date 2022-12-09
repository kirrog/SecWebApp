package ru.itmo.demoproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.demoproject.model.entity.DocumentEntity;
import ru.itmo.demoproject.model.entity.DocumentType;

import java.util.List;
import java.util.UUID;
@Repository
public interface DocumentRepository extends JpaRepository<DocumentEntity, UUID> {
    List<DocumentEntity> findAllByDocumentType(DocumentType documentType);
}
