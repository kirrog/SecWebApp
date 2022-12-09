package ru.itmo.demoproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.demoproject.model.entity.DocumentEntity;
import ru.itmo.demoproject.model.entity.DocumentType;
import ru.itmo.demoproject.model.entity.dto.CopyRequestDTO;
import ru.itmo.demoproject.model.entity.dto.ResponseBody;
import ru.itmo.demoproject.repository.DocRelationRepository;
import ru.itmo.demoproject.repository.DocumentRepository;
import ru.itmo.demoproject.repository.DocumentTypeRepository;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CopySevice {
    private final GoogleCopyService googleCopyService;
    private final DocumentRepository documentRepository;
    private final DocumentTypeRepository documentTypeRepository;

    // get file id and user id and send it to request
    public ResponseBody copyDocument(CopyRequestDTO copyRequestDTO) throws IOException {
        Optional<DocumentType> documentType = documentTypeRepository.findById(UUID.fromString(copyRequestDTO.getFileTypeID()));
        if (documentType.isEmpty()) {
            throw new NoSuchElementException("Document type with UUID: " + copyRequestDTO.getFileTypeID());
        }
        DocumentType documentTypeGetted = documentType.get();
        List<DocumentEntity> documentEntityList = documentRepository.findAllByDocumentType(documentTypeGetted);
        DocumentEntity documentEntity = documentEntityList.get(documentEntityList.size() - 1);
        return googleCopyService.makeCopy(copyRequestDTO.getUserEmail(), documentEntity.getToken(), documentTypeGetted);
    }
}
