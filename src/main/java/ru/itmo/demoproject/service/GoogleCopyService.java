package ru.itmo.demoproject.service;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itmo.demoproject.model.entity.DirectoryEntity;
import ru.itmo.demoproject.model.entity.DocumentEntity;
import ru.itmo.demoproject.model.entity.DocumentType;
import ru.itmo.demoproject.model.entity.UserEntity;
import ru.itmo.demoproject.repository.DirectoryEntityRepository;
import ru.itmo.demoproject.repository.DocumentEntityRepository;
import ru.itmo.demoproject.repository.UserEntityRepository;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GoogleCopyService {
    private final GoogleDriveService googleDriveService;
    private final DirectoryEntityRepository directoryEntityRepository;
    private final UserEntityRepository userEntityRepository;
    private final DocumentEntityRepository documentEntity;

    // get drive by email or drive id? and copy file by it id to created after registration file
    // creation of relation entity
    public DocumentEntity makeCopy(String email, String token, DocumentType documentType) throws IOException {
        Drive drive = googleDriveService.getDriveByEmail(email);
        File fileMetadata = new File();
        fileMetadata.setName(drive.files().get(token).execute().getName());
        UserEntity userEntity = userEntityRepository.findUserEntityByEmail(email);
        DirectoryEntity directoryEntity = directoryEntityRepository.findDirectoryEntityByOwner(userEntity);
        List<String> parents = List.of(directoryEntity.getToken());
        fileMetadata.setParents(parents);
        File copied = drive.files().copy(token, fileMetadata).execute();
        googleDriveService.createPublicPermission(drive, copied.getId());
        DocumentEntity copied_entity = DocumentEntity.builder()
                .id(UUID.randomUUID())
                .parent(directoryEntity)
                .documentType(documentType)
                .owner(userEntity)
                .token(copied.getId())
                .build();
        documentEntity.saveAndFlush(copied_entity);
        return copied_entity;
    }
}
